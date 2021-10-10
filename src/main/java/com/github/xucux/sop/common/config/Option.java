package com.github.xucux.sop.common.config;

import com.github.xucux.sop.module.http.manage.Proxy;

import java.util.*;

/**
 * @descriptions: 全局选项配置信息
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class Option {

    private String domain;

    private String userAgent;

    private LinkedHashMap<String, String> defaultCookies = new LinkedHashMap<>();

    private Map<String, Map<String, String>> cookies = new HashMap<>();

    private LinkedHashMap<String, String> headers = new LinkedHashMap<>();

    private String charset;

    private Proxy proxy;

    /**
     * 默认线程数
     */
    private int thread = 2;

    /**
     * 默认最大重试次数
     */
    private int retryMax = 2;

    /**
     * 请求后延时
     */
    private int sleepTime = 5000;

    /**
     * 重试延时
     */
    private int retrySleepTime = 1000;

    /**
     * 请求超时
     */
    private int timeOut = 30*1000;

    /**
     * 等待新地址的时间
     */
    private int waitNewUrlTime = 30 * 1000;

    public static Option make(){
        return new Option();
    }

    public int getWaitNewUrlTime() {
        return waitNewUrlTime;
    }

    public Option setWaitNewUrlTime(int waitNewUrlTime) {
        this.waitNewUrlTime = waitNewUrlTime;
        return this;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public Option setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Option setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Option setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public Map<String, String> getDefaultCookies() {
        return defaultCookies;
    }

    public Option addCookie(String name, String value) {
        defaultCookies.put(name, value);
        return this;
    }

    public Map<String, Map<String, String>> getCookies() {
        return cookies;
    }

    public Option addCookie(String domain, String name, String value) {
        if (!cookies.containsKey(domain)){
            cookies.put(domain,new HashMap<String, String>());
        }
        cookies.get(domain).put(name, value);
        return this;
    }

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public Option setHeaders(LinkedHashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Option addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public Option addHeader(String name,String value) {
        this.headers.put(name, value);
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Option setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public int getThread() {
        return thread;
    }

    public Option setThread(int thread) {
        this.thread = thread;
        return this;
    }

    public int getRetryMax() {
        return retryMax;
    }

    public Option setRetryMax(int retryMax) {
        this.retryMax = retryMax;
        return this;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public Option setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public int getRetrySleepTime() {
        return retrySleepTime;
    }

    public Option setRetrySleepTime(int retrySleepTime) {
        this.retrySleepTime = retrySleepTime;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Option setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }
}
