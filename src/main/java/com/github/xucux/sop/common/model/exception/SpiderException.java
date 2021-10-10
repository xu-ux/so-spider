package com.github.xucux.sop.common.model.exception;

/**
 * @descriptions: 爬虫异常处理类
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class SpiderException extends RuntimeException{

    /**
     * 自定义错误信息
     */
    private SpiderResult result;

    public SpiderException() {
        super();
    }

    public SpiderException(SpiderResult result) {
        super();
        this.result = result;
    }

    public SpiderException(String message) {
        super(message);
    }

    public SpiderException(String message,SpiderResult result) {
        super(message);
        this.result = result;
    }

    public SpiderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpiderException(String message,SpiderResult result, Throwable cause) {
        super(message, cause);
        this.result = result;
    }

    public SpiderException(Throwable cause) {
        super(cause);
    }

    public SpiderException(SpiderResult result,Throwable cause) {
        super(cause);
        this.result = result;
    }

}
