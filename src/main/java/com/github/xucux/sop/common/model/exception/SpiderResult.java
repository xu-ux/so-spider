package com.github.xucux.sop.common.model.exception;

/**
 * @descriptions: 错误信息
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class SpiderResult {

    private int code;

    private String msg;

    private Object data;


    public static SpiderResult success(Object data){
        return new SpiderResult(0,"请求成功", data);
    }

    public static SpiderResult fail(String msg){
        return new SpiderResult(500,msg);
    }

    public static SpiderResult defaultError(SpiderErrorCode errorCode){
        return new SpiderResult(errorCode.getCode(), errorCode.getDesc());
    }

    public SpiderResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public SpiderResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SpiderResult(int code) {
        this.code = code;
    }

    public SpiderResult() {
    }

    public int getCode() {
        return code;
    }

    public SpiderResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public SpiderResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public SpiderResult setData(Object data) {
        this.data = data;
        return this;
    }
}
