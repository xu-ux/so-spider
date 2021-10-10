package com.github.xucux.sop.module.core.scheduler.impl;

import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.filter.UrlFilter;
import com.github.xucux.sop.module.core.filter.impl.HashSetRemover;
import com.github.xucux.sop.module.core.scheduler.SpiderScheduler;
import com.github.xucux.sop.module.core.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @descriptions: 去重处理的调度器
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public abstract class RemovedScheduler implements SpiderScheduler {

    private static final Logger log = LoggerFactory.getLogger(RemovedScheduler.class);

    private UrlFilter urlFilter = new HashSetRemover();

    /**
     * 获取去重过滤器
     * @return
     */
    public UrlFilter getUrlFilter() {
        return urlFilter;
    }

    /**
     * 设置去重的过滤器
     * @param urlFilter
     * @return
     */
    public RemovedScheduler setUrlFilter(UrlFilter urlFilter) {
        this.urlFilter = urlFilter;
        log.debug("set removed scheduler is {}!",urlFilter.getClass());
        return this;
    }

    /**
     * 向任务队列添加request
     * @param request
     * @param task
     */
    @Override
    public void push(SRequest request, Task task) {
        // 如果不重复则添加成功
        if (!urlFilter.isDuplicate(request, task)){
            pushWhenNoDuplicate(request, task);
        }
    }

    /**
     * 向任务队列不重复的request的接口
     * @param request
     * @param task
     */
    protected abstract void pushWhenNoDuplicate(SRequest request, Task task);
}
