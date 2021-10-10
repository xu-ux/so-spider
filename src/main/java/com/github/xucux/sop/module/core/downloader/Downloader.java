package com.github.xucux.sop.module.core.downloader;


import com.github.xucux.sop.common.config.Option;
import com.github.xucux.sop.module.core.filter.HttpFilter;
import com.github.xucux.sop.module.core.session.SpiderSession;

import java.util.LinkedList;

/**
 * @descriptions: 下载器
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface Downloader {

    /**
     * 初始化下载器
     * @param option
     * @param downloadCallback
     */
    Downloader init(Option option, DownloadCallback downloadCallback);


    /**
     * 装配过滤器
     * @param httpFilters
     */
    void setHttpFilters(LinkedList<HttpFilter> httpFilters);

    /**
     * 获取队列中的任务数量
     * @return
     */
    int queueCount();

    /**
     * 获取正在执行中的任务数量
     * @return
     */
    int threadAliveCount();

    /**
     * 框架提交爬取任务
     * @param spiderSession 需要爬取的任务，被框架封装为 SpiderSession
     */
    void download(SpiderSession spiderSession);
}
