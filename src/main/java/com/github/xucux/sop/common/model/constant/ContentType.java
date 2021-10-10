package com.github.xucux.sop.common.model.constant;

import java.io.Serializable;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public enum ContentType implements Serializable {
    /**
     * json传参
     */
    JSON("application/json"),
    /**
     * xml传参
     */
    XML("text/xml"),
    FORM("application/x-www-form-urlencoded"),
    MULTIPART("multipart/form-data"),
    ;

    private final String name;

    ContentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
