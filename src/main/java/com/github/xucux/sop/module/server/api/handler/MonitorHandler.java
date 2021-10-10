package com.github.xucux.sop.module.server.api.handler;

import com.alibaba.fastjson.JSONObject;

import com.github.xucux.sop.common.model.exception.SpiderResult;
import com.github.xucux.sop.module.core.listener.impl.MonitorListener;
import com.github.xucux.sop.module.server.api.model.Request;
import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.function.Function;

/**
 * @descriptions: 监控处理器
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class MonitorHandler extends SpiderHttpHandlerManager{

    @Override
    public Function<HttpExchange, JSONObject> get() {
        MonitorListener listener = MonitorListener.getInstance();
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.success(
                new HashMap<String,String>(){{
                    put("error",String.valueOf(listener.getErrorCount().get()));
                    put("errorUrls",listener.getErrorUrls().toString());
                    put("success",String.valueOf(listener.getSuccessCount().get()));
                }}
        )));
    }

    @Override
    public Function<HttpExchange, JSONObject> postJson() {
        Request request = getRequest(getHttpExchange());
        return httpExchange ->  JSONObject.parseObject(request.getJson().toJSONString());
    }

    @Override
    public Function<HttpExchange, JSONObject> postForm() {
        Request request = getRequest(getHttpExchange());
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(request.getQuery()));
    }

    @Override
    public Function<HttpExchange, JSONObject> postMulit() {
        return httpExchange -> JSONObject.parseObject(JSONObject.toJSONString(SpiderResult.fail("请求失败")));
    }


}
