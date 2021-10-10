package com.github.xucux.sop.common.model.request.impl;



import com.github.xucux.sop.common.model.constant.HttpMethod;
import com.github.xucux.sop.common.model.pojo.RequestBody;
import com.github.xucux.sop.common.model.request.IRequest;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @descriptions: 请求需要信息
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class SRequest implements Serializable , IRequest {

    /** 请求的url地址 */
    private String url;

    /** 请求方式 */
    private HttpMethod method;

    /** 请求体 */
    private RequestBody requestBody;

    /** 处理器的包装名称 */
    private String processorWrapper;

    /** user-agent */
    private String userAgent;

    /** referer */
    private String referer;

    /** cookies信息 */
    private LinkedHashMap<String, String> cookies = new LinkedHashMap<>();

    /** 其他头信息 */
    private LinkedHashMap<String, String> headers = new LinkedHashMap<>();

    /** 扩展信息 */
    private Map<String, Object> extras;

    /** 请求的优先级，值越大越优先处理，负数最后处理，正整数优先处理，0默认在正数后处理 */
    private long priority = 0;

    /** 请求重试次数 */
    private volatile int retryCount = 0;

    public SRequest(String url) {
        this.url = url;
    }

    public SRequest(String url,String processorWrapperName) {
        this.url = url;
        this.method = HttpMethod.GET;
        this.processorWrapper = processorWrapperName;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public SRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public SRequest setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public SRequest setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public SRequest setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getProcessorWrapper() {
        return processorWrapper;
    }

    public SRequest setProcessorWrapper(String processorWrapper) {
        this.processorWrapper = processorWrapper;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public SRequest setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public LinkedHashMap<String, String> getCookies() {
        return cookies;
    }

    public SRequest setCookies(LinkedHashMap<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public SRequest setHeaders(LinkedHashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public SRequest setExtras(Map<String, Object> extras) {
        this.extras = extras;
        return this;
    }

    public String getReferer() {
        return referer;
    }

    public SRequest setReferer(String referer) {
        this.referer = referer;
        return this;
    }

    public long getPriority() {
        return priority;
    }

    public SRequest setPriority(long priority) {
        this.priority = priority;
        return this;
    }
}
