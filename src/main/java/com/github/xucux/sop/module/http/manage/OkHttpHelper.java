package com.github.xucux.sop.module.http.manage;

import com.alibaba.fastjson.JSON;
import com.github.xucux.sop.common.config.Option;
import com.github.xucux.sop.common.model.constant.HttpConst;
import com.github.xucux.sop.common.model.constant.HttpMethod;
import com.github.xucux.sop.common.model.exception.SpiderException;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author xucl
 * @Version 1.0
 * @ClassName OkHttpHelper
 * @Description
 * @date 2021/6/18
 */
public class OkHttpHelper implements HttpConst {

    private static volatile OkHttpClient okHttpClient = null;
    private static volatile Semaphore semaphore = null;
    private Request.Builder request;
    private OkHttpClient.Builder builder;


    /*** 参数信息 ***/
    private LinkedHashMap<String, String> defaultCookies = new LinkedHashMap<>();
    private LinkedHashMap<String, String> cookies = new LinkedHashMap<>();
    private Map<String, String> headerMap;
    private Map<String, String> paramMap;
    private String url;
    private Option option;
    private SRequest sRequest;


    /**
     * 初始化okHttpClient，并且允许https访问
     */
    private OkHttpHelper() {
        if (okHttpClient == null) {
            synchronized (OkHttpHelper.class) {
                if (okHttpClient == null) {
                    TrustManager[] trustManagers = buildTrustManagers();
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory(trustManagers), (X509TrustManager) trustManagers[0])
                            .hostnameVerifier((hostName, session) -> true)
                            .retryOnConnectionFailure(true)
                            .build();
                }
            }
        }
    }

    /**
     * 初始化okHttpClient，并且允许https访问
     */
    private OkHttpHelper(Proxy proxy) {
        if (okHttpClient == null) {
            synchronized (OkHttpHelper.class) {
                if (okHttpClient == null) {
                    TrustManager[] trustManagers = buildTrustManagers();
                    okHttpClient = new OkHttpClient.Builder()
                            .proxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxy.getHost(), proxy.getPort())))
                            .proxyAuthenticator(new Authenticator() {
                                @Override
                                public Request authenticate(Route route, Response response) throws IOException {
                                    //设置代理服务器账号密码
                                    String credential = Credentials.basic(proxy.getUsername(), proxy.getPassword());
                                    return response.request().newBuilder()
                                            .header("Proxy-Authorization", credential)
                                            .build();
                                }
                            })
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory(trustManagers), (X509TrustManager) trustManagers[0])
                            .hostnameVerifier((hostName, session) -> true)
                            .retryOnConnectionFailure(true)
                            .build();
                }
            }
        }
    }



    /**
     * 用于异步请求时，控制访问线程数，返回结果
     *
     * @return
     */
    private static Semaphore getSemaphoreInstance() {
        //只能1个线程同时访问
        synchronized (OkHttpHelper.class) {
            if (semaphore == null) {
                semaphore = new Semaphore(0);
            }
        }
        return semaphore;
    }

    /**
     * 创建OkHttpHelper
     *
     * @return
     */
    public static OkHttpHelper builder(Option option) {
        return new OkHttpHelper().setOption(option);
    }


    public static OkHttpHelper proxyBuilder(Option option){
        if (null != option.getProxy()){
            return new OkHttpHelper(option.getProxy()).setOption(option);
        }
        return new OkHttpHelper().setOption(option);
    }

    private Option getOption() {
        return option;
    }

    private OkHttpHelper setOption(Option option) {
        this.option = option;
        this.defaultCookies = option.getHeaders();
        this.headerMap = option.getHeaders();
        if (StringUtils.isNotBlank(option.getUserAgent())){
            this.headerMap.put(Header.USER_AGENT,option.getUserAgent());
        }
        return this;
    }

    /**
     * 添加请求
     * @param request
     * @return
     */
    public OkHttpHelper addRequest(SRequest request){
        this.url = request.getUrl();
        this.headerMap.putAll(request.getHeaders());

        if (StringUtils.isNotBlank(request.getUserAgent())){
            this.headerMap.put(Header.USER_AGENT,request.getUserAgent());
        }
        if (StringUtils.isNotBlank(request.getReferer())){
            this.headerMap.put(Header.REFERER,request.getReferer());
        }
        this.cookies = request.getCookies();
        this.sRequest = request;
        return this;
    }





    private void  checkRequest(){
        if (this.sRequest == null){
            throw new SpiderException("请求异常，请求信息不存在");
        }
    }

    /**
     * 初始化request
     *
     * @return
     */
    public OkHttpHelper send() {
        checkRequest();
        HttpMethod method = this.sRequest.getMethod();

        if (HttpMethod.GET.equals(method)){
            com.github.xucux.sop.common.model.pojo.RequestBody requestBody = this.sRequest.getRequestBody();
            String body = "";
            if (null != requestBody){
                body = requestBody.getRequestBody(method);
            }
            request = new Request.Builder().get().url(url.concat(body));
        } else if (HttpMethod.POST_JSON.equals(method)){
            com.github.xucux.sop.common.model.pojo.RequestBody requestBody = this.sRequest.getRequestBody();
            String body = requestBody.getRequestBody(method);
            RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
            request = new Request.Builder().post(reqBody).url(url);
        } else if (HttpMethod.POST_FORM.equals(method)) {
            FormBody.Builder formBody = new FormBody.Builder();
            if (paramMap != null) {
                paramMap.forEach(formBody::add);
            }
            request = new Request.Builder().post(formBody.build()).url(url);
        }
        return this;
    }

    /**
     * 初始化get方法
     *
     * @return
     */
    public OkHttpHelper get() {
        request = new Request.Builder().get();
        StringBuilder urlBuilder = new StringBuilder(url);
        if (paramMap != null) {
            urlBuilder.append("?");
            try {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    urlBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8")).
                            append("=").
                            append(URLEncoder.encode(entry.getValue(), "utf-8")).
                            append("&");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        request.url(urlBuilder.toString());
        return this;
    }

    /**
     * 初始化post方法
     *
     * @param isJsonPost true等于json的方式提交数据，类似postman里post方法的raw
     *                   false等于普通的表单提交
     * @return
     */
    public OkHttpHelper post(boolean isJsonPost) {
        RequestBody requestBody;
        if (isJsonPost) {
            String json = "";
            if (paramMap != null) {
                json = JSON.toJSONString(paramMap);
            }
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } else {
            FormBody.Builder formBody = new FormBody.Builder();
            if (paramMap != null) {
                paramMap.forEach(formBody::add);
            }
            requestBody = formBody.build();
        }
        request = new Request.Builder().post(requestBody).url(url);
        return this;
    }

    /**
     * 同步请求
     *
     * @return
     */
    public Response sync() throws SpiderException {
        setHeader(request);
        try {
            Response response = okHttpClient.newCall(request.build()).execute();
            return response;
        } catch (Exception e) {
            throw new SpiderException("请求出现异常",e);
        }
    }

    /**
     * 同步请求
     *
     * @return
     */
    public String syncTwo() throws SpiderException {
        setHeader(request);
        try {
            Response response = okHttpClient.newCall(request.build()).execute();
            assert response.body() != null;
            return response.body().string();
        } catch (Exception e) {
            throw new SpiderException("请求失败",e);
        }
    }

    /**
     * 异步请求，有返回值
     */
    public String async() {
        StringBuilder buffer = new StringBuilder("");
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                buffer.append("请求出错：").append(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                buffer.append(response.body().string());
                getSemaphoreInstance().release();
            }
        });
        try {
            getSemaphoreInstance().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 异步请求，带有接口回调
     *
     * @param callBack
     */
    public void async(ICallBack callBack) {
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                callBack.onSuccessful(call, response.body().string());
            }
        });
    }

    /**
     * 为request添加请求头
     *
     * @param request
     */
    private void setHeader(Request.Builder request) {
        if (headerMap != null) {
            try {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (defaultCookies != null && cookies == null){
            try {
                for (Map.Entry<String, String> entry : defaultCookies.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cookies != null){
            try {
                for (Map.Entry<String, String> entry : cookies.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    private void addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new LinkedHashMap<>(16);
        }
        paramMap.put(key, value);
    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory(TrustManager[] trustAllCerts) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    /**
     * 自定义一个接口回调
     */
    public interface ICallBack {

        void onSuccessful(Call call, String data);

        void onFailure(Call call, String errorMsg);

    }

}
