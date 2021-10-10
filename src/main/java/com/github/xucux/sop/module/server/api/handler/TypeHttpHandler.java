package com.github.xucux.sop.module.server.api.handler;

import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.constant.HttpMethod;
import com.github.xucux.sop.common.model.exception.SpiderResult;
import com.sun.net.httpserver.HttpExchange;

import java.util.function.Function;

/**
 * @descriptions: 请求类型处理
 * @author: xucl
 * @date: 2021/8/25
 * @version: 1.0
 */
public interface TypeHttpHandler {

    /**
     * 分发器
     * @param method
     * @return
     */
    default Function<HttpExchange, JSONObject> dispatch(HttpMethod method){
        switch (method){
            case GET:
                return get();
            case POST_JSON:
                return postJson();
            case POST_FORM:
                return postForm();
            case POST_MULTIPART:
                return postMulit();
            default:
                return extend();
        }
    }

    /**
     * 接收get请求时，返回处理方法
     * @return
     */
    default Function<HttpExchange, JSONObject> get(){
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.fail("请求失败")));
    }

    /**
     * 接收post-json请求时，返回处理方法
     * @return
     */
    default Function<HttpExchange, JSONObject> postJson(){
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.fail("请求失败")));
    }

    /**
     * 接收post-x-www-form-urlencoded请求时，返回处理方法
     * @return
     */
    default Function<HttpExchange, JSONObject> postForm(){
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.fail("请求失败")));
    }

    /**
     * 接收post-multipart/form-data请求时，返回处理方法
     * @return
     */
    default Function<HttpExchange, JSONObject> postMulit(){
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.fail("请求失败")));
    }

    /**
     * 接收other请求时，返回处理方法
     * @return
     */
    default Function<HttpExchange, JSONObject> extend(){
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.fail("请求失败")));
    }

}
