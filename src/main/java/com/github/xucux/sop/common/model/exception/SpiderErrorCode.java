package com.github.xucux.sop.common.model.exception;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public enum SpiderErrorCode {


    ;

    private final int code;

    private final String desc;


    SpiderErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
