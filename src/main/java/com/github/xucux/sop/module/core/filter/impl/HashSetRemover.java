package com.github.xucux.sop.module.core.filter.impl;


import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.module.core.filter.UrlFilter;
import com.github.xucux.sop.module.core.task.Task;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @descriptions: 本地Hash过滤器去重
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class HashSetRemover implements UrlFilter {

//    private volatile Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap());
    private ConcurrentSkipListSet<String> urls = new ConcurrentSkipListSet<>();


    /**
     * 是否重复
     *
     * @param request 请求信息
     * @param task    任务
     * @return true重复
     */
    @Override
    public boolean isDuplicate(IRequest request, Task task) {
        return !urls.add(request.getUrl());
    }

    /**
     * 重置布隆过滤器
     *
     * @param task
     */
    @Override
    public void reset(Task task) {
        this.urls.clear();
    }

    /**
     * 重置布隆过滤器
     *
     * @param task 任务
     * @param urls 需要参与过滤的url地址
     */
    @Override
    public void reset(Task task, Set<String> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
    }

    /**
     * 获取不重复的请求总数量
     *
     * @param task 任务
     * @return
     */
    @Override
    public int getRequestCount(Task task) {
        return  this.urls.size();
    }
}
