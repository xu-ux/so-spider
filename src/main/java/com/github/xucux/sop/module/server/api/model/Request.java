package com.github.xucux.sop.module.server.api.model;

import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.constant.HttpMethod;


import java.util.Map;

/**
 * @descriptions: 请求信息
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class Request {

    private HttpMethod httpMethod;


    private Map<String,String> query;


    private JSONObject json;


    private byte[] body;


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Request setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public Request setQuery(Map<String, String> query) {
        this.query = query;
        return this;
    }

    public JSONObject getJson() {
        return json;
    }

    public Request setJson(JSONObject json) {
        this.json = json;
        return this;
    }

    public byte[] getBody() {
        return body;
    }

    public Request setBody(byte[] body) {
        this.body = body;
        return this;
    }
}
