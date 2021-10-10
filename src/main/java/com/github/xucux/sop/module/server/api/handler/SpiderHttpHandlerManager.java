package com.github.xucux.sop.module.server.api.handler;

import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.constant.HttpConst;
import com.github.xucux.sop.common.model.constant.HttpMethod;
import com.github.xucux.sop.common.util.QueryUtils;
import com.github.xucux.sop.module.server.api.model.Request;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @descriptions: 监控服务接口
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public abstract class SpiderHttpHandlerManager implements HttpHandler,SpiderHttpHandler , HttpConst {

    private static final Logger log = LoggerFactory.getLogger(SpiderHttpHandlerManager.class);

    private HttpExchange httpExchange;

    //public abstract Function<HttpExchange, JSONObject> handle();

    public HttpExchange getHttpExchange() {
        return httpExchange;
    }

    /**
     * 服务处理器
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        Request request = getRequest(httpExchange);
        JSONObject jsonObject = null;
        if (null != request){
            HttpMethod httpMethod = request.getHttpMethod();
            jsonObject = dispatch(httpMethod).apply(httpExchange);
        }
        jsonObject = extend().apply(httpExchange);
        handleResponse(httpExchange,jsonObject);
    }

    /**
     * 获取请求头信息
     * @param httpExchange
     * @return
     */
    @Override
    public Map<String, List<String>> getHeaders(HttpExchange httpExchange) {
        Headers headers = httpExchange.getRequestHeaders();
        Map<String, List<String>> map = headers.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return map;
    }

    /**
     * 获取请求参数
     * @param httpExchange
     * @return
     * @throws Exception
     */
    @Override
    public String getRequestParam(HttpExchange httpExchange) {
        try {
            String paramStr = "";
            String requestMethod = httpExchange.getRequestMethod();

            if (requestMethod.equals(Method.GET)) {
                // GET请求读queryString
                paramStr = httpExchange.getRequestURI().getQuery();
            } else if (requestMethod.equals(Method.POST)){
                // 非GET请求读请求体
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), UTF_8));
                StringBuilder requestBodyContent = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    requestBodyContent.append(line);
                }
                paramStr = requestBodyContent.toString();
            }

            return paramStr;
        } catch (Exception e) {
            log.error("获取参数异常",e);
            return "";
        }
    }

    /**
     * 获取请求参数
     * @param httpExchange
     * @return
     * @throws Exception
     */
    @Override
    public Request getRequest(HttpExchange httpExchange) {
        try {

            String requestMethod = httpExchange.getRequestMethod();
            String contentType = httpExchange.getRequestHeaders().getFirst(ContentType.KEY);

            // GET请求读queryString
            if (requestMethod.equals(Method.GET)) {

                String paramStr = httpExchange.getRequestURI().getQuery();

                return new Request().setHttpMethod(HttpMethod.GET)
                        .setQuery(QueryUtils.split(paramStr));

            }
            // POST-JSON读取body
            else if (requestMethod.equals(Method.POST) && contentType.contains(ContentType.JSON_KEY)){

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), UTF_8));
                StringBuilder requestBodyContent = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    requestBodyContent.append(line);
                }
                return new Request().setHttpMethod(HttpMethod.POST_JSON)
                        .setJson(JSONObject.parseObject(requestBodyContent.toString()));
            }
            // POST-form读取body
            else if (requestMethod.equals(Method.POST) && contentType.contains(ContentType.FORM_KEY)){
                // 非GET请求读请求体
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), UTF_8));
                StringBuilder requestBodyContent = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    requestBodyContent.append(line);
                }
                return new Request().setHttpMethod(HttpMethod.POST_FORM)
                        .setQuery(QueryUtils.split(requestBodyContent.toString()));
            }
            // POST-formData暂不处理
            else if (requestMethod.equals(Method.POST) && contentType.contains(ContentType.MULTIPART_KEY)){

                return new Request().setHttpMethod(HttpMethod.POST_MULTIPART);
            }
            // PUT DELETE等等其他暂不处理
            else {
                return null;
            }

        } catch (Exception e) {
            log.error("获取参数异常",e);
            return null;
        }
    }

    /**
     * 返回json
     * @param httpExchange
     * @param jsonObject
     */
    @Override
    public void handleResponse(HttpExchange httpExchange, JSONObject jsonObject) {
        try {
            byte[] responseContentByte = jsonObject.toJSONString().getBytes("utf-8");

            //设置响应头
            httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");

            //设置响应码和响应体长度
            httpExchange.sendResponseHeaders(200, responseContentByte.length);

            OutputStream out = httpExchange.getResponseBody();
            out.write(responseContentByte);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回html
     * @param httpExchange
     * @param html
     */
    @Override
    public void handleResponse(HttpExchange httpExchange, String html)  {
        try {
            byte[] responseContentByte = html.getBytes("utf-8");

            //设置响应头，必须在sendResponseHeaders方法之前设置！
            httpExchange.getResponseHeaders().add("Content-Type", "text/html;charset=utf-8");

            //设置响应码和响应体长度，必须在getResponseBody方法之前调用！
            httpExchange.sendResponseHeaders(200, responseContentByte.length);

            OutputStream out = httpExchange.getResponseBody();
            out.write(responseContentByte);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
