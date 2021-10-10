package com.github.xucux.sop.common.model.pojo;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class MonitorCount {

    /**
     * 队列长度
     */
    private Integer queueSize;

    /**
     * 去重后的url数量
     */
    private Integer requestCount;

    /**
     * 默认无优先级队列长度
     */
    private Integer noPriorityQueueSize;

    /**
     * 最先优先级队列长度
     */
    private Integer priorityQueueFirstSize;

    /**
     * 最后优先级队列长度
     */
    private Integer priorityQueueLastSize;

    public Integer getQueueSize() {
        return queueSize;
    }

    public MonitorCount setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
        return this;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public MonitorCount setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
        return this;
    }

    public Integer getNoPriorityQueueSize() {
        return noPriorityQueueSize;
    }

    public MonitorCount setNoPriorityQueueSize(Integer noPriorityQueueSize) {
        this.noPriorityQueueSize = noPriorityQueueSize;
        return this;
    }

    public Integer getPriorityQueueFirstSize() {
        return priorityQueueFirstSize;
    }

    public MonitorCount setPriorityQueueFirstSize(Integer priorityQueueFirstSize) {
        this.priorityQueueFirstSize = priorityQueueFirstSize;
        return this;
    }

    public Integer getPriorityQueueLastSize() {
        return priorityQueueLastSize;
    }

    public MonitorCount setPriorityQueueLastSize(Integer priorityQueueLastSize) {
        this.priorityQueueLastSize = priorityQueueLastSize;
        return this;
    }
}
