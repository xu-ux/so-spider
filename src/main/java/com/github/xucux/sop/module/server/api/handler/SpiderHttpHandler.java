package com.github.xucux.sop.module.server.api.handler;

import com.alibaba.fastjson.JSONObject;

import com.github.xucux.sop.module.server.api.model.Request;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface SpiderHttpHandler extends TypeHttpHandler{

    /**
     * 获取请求头信息
     * @param httpExchange
     * @return
     */
    Map<String, List<String>> getHeaders(HttpExchange httpExchange);

    /**
     * 获取请求体信息
     * @param httpExchange
     * @return
     */
    String getRequestParam(HttpExchange httpExchange);


    /**
     * 获取请求体信息
     * @param httpExchange
     * @return
     */
    Request getRequest(HttpExchange httpExchange);

    /**
     * 返回json
     * @param httpExchange
     * @param jsonObject
     */
    void handleResponse(HttpExchange httpExchange,JSONObject jsonObject);

    /**
     * 返回html
     * @param httpExchange
     * @param html
     */
    void handleResponse(HttpExchange httpExchange,String html);
}
