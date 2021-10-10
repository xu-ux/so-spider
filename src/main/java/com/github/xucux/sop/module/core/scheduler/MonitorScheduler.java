package com.github.xucux.sop.module.core.scheduler;


import com.github.xucux.sop.common.model.pojo.MonitorCount;
import com.github.xucux.sop.module.core.task.Task;

/**
 * @descriptions: 监控调度信息
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface MonitorScheduler {

    int getSurplusRequestsCount(Task task);

    int getTotalRequestsCount(Task task);

    default MonitorCount getPriorityRequestsCount(Task task){
        return new MonitorCount().setRequestCount(getTotalRequestsCount(task))
                .setQueueSize(getSurplusRequestsCount(task));
    }
}
