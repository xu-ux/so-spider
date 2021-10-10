package com.github.xucux.sop.module.core.scheduler;


import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.task.Task;

/**
 * @descriptions: 请求任务的获取和存储调度器
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface SpiderScheduler {

    /**
     * 存储请求
     *
     * @param request
     * @param task
     */
    void push(SRequest request, Task task);

    /**
     * 取出请求
     *
     * @param task
     * @return
     */
    SRequest poll(Task task);
}
