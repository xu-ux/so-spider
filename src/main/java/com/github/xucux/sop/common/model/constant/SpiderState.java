package com.github.xucux.sop.common.model.constant;

/**
 * @descriptions: 爬虫运行状态
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public enum SpiderState {

    /**
     * 未启动
     */
    INIT("初始化"),
    /**
     * 运行中
     */
    RUNNING("运行中"),
    /**
     * 已关闭
     */
    STOP("已关闭");

    private String name;

    SpiderState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
