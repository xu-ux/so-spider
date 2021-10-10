package com.github.xucux.sop.module.core.scheduler.impl;


import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.scheduler.MonitorScheduler;
import com.github.xucux.sop.module.core.scheduler.SpiderScheduler;
import com.github.xucux.sop.module.core.task.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @descriptions: 本地队列调度器
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public class QueueScheduler extends RemovedScheduler implements SpiderScheduler, MonitorScheduler {

    private BlockingQueue<SRequest> queue = new LinkedBlockingQueue<SRequest>();


    @Override
    public synchronized SRequest poll(Task task) {
        return queue.poll();
    }

    /**
     * 添加请求任务至队列的具体实现
     * @param request
     * @param task
     */
    @Override
    protected void pushWhenNoDuplicate(SRequest request, Task task) {
        queue.add(request);
    }

    @Override
    public int getSurplusRequestsCount(Task task) {
        return queue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getUrlFilter().getRequestCount(task);
    }
}
