package com.github.xucux.sop.module.core.scheduler.impl;


import com.github.xucux.sop.common.model.pojo.MonitorCount;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.scheduler.MonitorScheduler;
import com.github.xucux.sop.module.core.scheduler.SpiderScheduler;
import com.github.xucux.sop.module.core.task.Task;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @descriptions: 优先级队列调度器
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class PriorityScheduler extends RemovedScheduler implements SpiderScheduler, MonitorScheduler {

    /**
     * 初始容量
     */
    public static final int INITIAL_CAPACITY = 5;

    /**
     * 普通级别，在第一级别后处理
     */
    private BlockingQueue<SRequest> noPriorityQueue = new LinkedBlockingQueue<SRequest>();

    /**
     * 优先级第一级别，最先处理
     */
    private PriorityBlockingQueue<SRequest> priorityQueueFirst = new PriorityBlockingQueue<SRequest>(INITIAL_CAPACITY,((o1, o2) -> NumberUtils.compare(o1.getPriority(),o2.getPriority())));

    /**
     * 优先级最后的级别，最后处理
     */
    private PriorityBlockingQueue<SRequest> priorityQueueLast = new PriorityBlockingQueue<SRequest>(INITIAL_CAPACITY,((o1, o2) -> NumberUtils.compare(o1.getPriority(),o2.getPriority())));


    @Override
    public int getSurplusRequestsCount(Task task) {
        return noPriorityQueue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getUrlFilter().getRequestCount(task);
    }

    @Override
    public MonitorCount getPriorityRequestsCount(Task task) {
        return new MonitorCount().setRequestCount(getTotalRequestsCount(task))
                .setNoPriorityQueueSize(noPriorityQueue.size())
                .setPriorityQueueFirstSize(priorityQueueFirst.size())
                .setPriorityQueueLastSize(priorityQueueLast.size());
    }

    @Override
    public SRequest poll(Task task) {
        SRequest poll = priorityQueueFirst.poll();
        if (poll != null) {
            return poll;
        }
        poll = noPriorityQueue.poll();
        if (poll != null) {
            return poll;
        }
        return priorityQueueLast.poll();
    }

    @Override
    protected void pushWhenNoDuplicate(SRequest request, Task task) {
        if (request.getPriority() == 0) {
            noPriorityQueue.add(request);
        } else if (request.getPriority() > 0) {
            priorityQueueFirst.put(request);
        } else {
            priorityQueueLast.put(request);
        }
    }
}
