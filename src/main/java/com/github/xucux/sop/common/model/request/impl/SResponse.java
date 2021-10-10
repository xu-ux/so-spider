package com.github.xucux.sop.common.model.request.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.constant.HttpConst;
import com.github.xucux.sop.common.model.constant.ResponseType;
import com.github.xucux.sop.common.model.request.IResponse;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class SResponse implements IResponse, HttpConst {

    private static Logger log = LoggerFactory.getLogger(SResponse.class);

    private Response response;

    private ResponseType type;

    private Map<String, String> headers;

    private byte[] body = null;

    private String bodyString = null;

    public SResponse(Response response) {
        this.response = response;
    }

    @Override
    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    @Override
    public int getHttpCode() {
        return response.code();
    }

    @Override
    public String getHttpMessage() {
        return response.message();
    }


    @Override
    public String getHeader(String name) {
        return response.header(name);
    }

    public String getHeader(String name, String defaultValue) {
        return response.header(name, defaultValue);
    }

    @Override
    public Map<String, String> getHeaders() {
        if (headers == null) {
            Headers headerList = response.headers();
            headers = new HashMap<>(headerList.size());
            headerList.forEach(pair ->
                    headers.put(pair.getFirst(), pair.getSecond())
            );
        }
        return headers;
    }


    @Override
    public String getCookie() {
        return getHeader(Header.COOKIE);
    }

    @Override
    public ResponseType getContentType() {
        if (response == null){
            return null;
        }
        if (type == null) {

            MediaType mediaType = response.body().contentType();

            String typeStr = mediaType.type();

            type = ResponseType.ofName(typeStr);
        }
        return type;
    }

    @Override
    public byte[] getBody() throws Exception{
        if (response == null){
            return null;
        }
        if (body == null) {
            body = response.body().bytes();
        }
        return body;
    }

    @Override
    public String getBodyString() {
        if (response == null){
            return null;
        }
        if (this.bodyString == null) {
            try {
                this.bodyString = new String(getBody());
            } catch (Exception e) {
                log.debug("获取body异常",e);
            }
        }
        return bodyString;
    }

    @Override
    public Document parseByJsoup() {
        String html = getBodyString();
        if (StringUtils.isBlank(html)){
            return null;
        }
        return Jsoup.parse(html);
    }

    @Override
    public JXDocument parseByXpathJsoup() {
        String html = getBodyString();
        if (StringUtils.isBlank(html)){
            return null;
        }
        return JXDocument.create(html);
    }

    @Override
    public JSONObject parseByJson() {
        String json = getBodyString();
        if (StringUtils.isBlank(json)){
            return null;
        }
        return JSON.parseObject(json);
    }

    @Override
    public <T> T parseByJson(Class<T> clazz) {
        String json = getBodyString();
        T t = null;
        if (json != null) {
            t = JSON.parseObject(json, clazz);
        }
        return t;
    }
}
