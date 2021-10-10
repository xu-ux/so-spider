package com.github.xucux.sop.common.model.constant;

import java.util.Arrays;

/**
 * @descriptions: 响应类型
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public enum ResponseType {

    /**
     * json传参
     */
    JSON("application/json"),
    XHTML("application/xhtml+xml"),
    XML("application/xml"),
    Atom_XML("application/atom+xml"),
    PDF("application/pdf"),
    WORD("application/msword"),
    STREAM("application/octet-stream"),
    FORM("application/x-www-form-urlencoded"),
    MULTIPART("multipart/form-data"),
    XML_("text/xml"),
    HTML_("text/html"),
    TEXT("text/plain"),
    GIT("image/gif"),
    JPG("image/jpeg"),
    PNG("image/png"),


    ;

    public static ResponseType ofName(String name){
       return Arrays.asList(ResponseType.values()).stream()
                .filter(e -> name.toLowerCase().contains(e.getName()))
                .findFirst().orElse(TEXT);
    }

    private final String name;


    ResponseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
