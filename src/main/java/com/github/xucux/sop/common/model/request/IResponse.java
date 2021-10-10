package com.github.xucux.sop.common.model.request;

import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.constant.ResponseType;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;

import java.util.Map;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface IResponse {

    /**
     * 查询响应的状态, 表示请求是否成功
     * @return 请求成功: true
     */
    boolean isSuccessful();

    /**
     * 返回HTTP响应码
     * @return 正常情况: 200
     */
    int getHttpCode();

    /**
     * 获取 HTTP响应提示
     * @return OK 或者其他
     */
    String getHttpMessage();

    /**
     * 获取请求头
     * @param name 请求头名字
     * @return
     */
    String getHeader(String name);

    /**
     * 获取所有 Header 的 key-value
     * @return
     */
    Map<String, String> getHeaders();

    /**
     * 获取cookie
     * @return
     */
    String getCookie();

    ResponseType getContentType();

    byte[] getBody() throws Exception;

    String getBodyString();

    Document parseByJsoup();

    JXDocument parseByXpathJsoup();

    JSONObject parseByJson();

    <T> T parseByJson(Class<T> clazz);
}
