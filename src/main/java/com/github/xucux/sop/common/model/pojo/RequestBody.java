package com.github.xucux.sop.common.model.pojo;


import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.constant.ContentType;
import com.github.xucux.sop.common.model.constant.HttpMethod;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class RequestBody implements Serializable{

    private byte[] body;

    private ContentType contentType;

    private Map<String, String> bodyMap = new HashMap<>();

    private String bodyString;

    private String encoding;

    public String getRequestBody(HttpMethod httpMethod) {
        if (bodyString != null) {
            return bodyString;
        }
        switch (httpMethod){
            case POST_FORM:
                bodyString = parseForm();
                break;
            case POST_JSON:
                bodyString = parseJson();
                break;
            case GET:
                bodyString = parseQuery();
                break;
            default:
                break;
        }
        return bodyString;
    }

    private String parseQuery() {
        StringBuilder urlBuilder = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : bodyMap.entrySet()) {
                urlBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8")).
                        append("=").
                        append(URLEncoder.encode(entry.getValue(), "utf-8")).
                        append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        return urlBuilder.toString();
    }

    private String parseForm() {
        StringBuilder stringBuilder = new StringBuilder();
        if (null != bodyMap && !bodyMap.isEmpty()){
            for (Map.Entry<String, String> entry : bodyMap.entrySet()) {
                if (stringBuilder.length() > 1) {
                    stringBuilder.append("&")
                            .append(entry.getKey())
                            .append("=")
                            .append(entry.getValue());
                } else {
                    stringBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue());
                }
            }
        }
        bodyString = stringBuilder.toString();
        return bodyString;
    }

    private String parseJson() {
        bodyString = JSONObject.toJSONString(bodyMap);
        return bodyString;
    }

    public byte[] getBody() {
        return body;
    }

    public RequestBody setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public RequestBody setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public RequestBody setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }
}
