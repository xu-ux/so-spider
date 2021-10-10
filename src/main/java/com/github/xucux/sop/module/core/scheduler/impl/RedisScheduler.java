package com.github.xucux.sop.module.core.scheduler.impl;


import com.github.xucux.sop.common.model.constant.RedissonConst;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.filter.impl.RedisBloomFilterRemover;
import com.github.xucux.sop.module.core.scheduler.MonitorScheduler;
import com.github.xucux.sop.module.core.scheduler.SpiderScheduler;
import com.github.xucux.sop.module.core.task.Task;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;

/**
 * @descriptions: 分布式队列调度器
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class RedisScheduler extends RemovedScheduler implements SpiderScheduler, MonitorScheduler, RedissonConst {

    /**
     * redis客户端
     */
    private RedissonClient redissonClient;

    /**
     * 分布式队列调度器采用分布式布隆过滤器处理器去重逻辑
     * @see RedisBloomFilterRemover
     * @param redissonClient
     */
    public RedisScheduler(RedissonClient redissonClient,Task task) {
        this.redissonClient = redissonClient;
        setUrlFilter(new RedisBloomFilterRemover(redissonClient,task));
    }


    private String getQueueKey(Task task){
        return QUEUE_LIST_PREFIX.concat(task.getTaskId());
    }

    @Override
    public synchronized SRequest poll(Task task) {
        RQueue<SRequest> queue = redissonClient.<SRequest>getBlockingQueue(getQueueKey(task));
        return queue.poll();
    }

    @Override
    protected void pushWhenNoDuplicate(SRequest request, Task task) {
        RQueue<SRequest> queue = redissonClient.<SRequest>getBlockingQueue(getQueueKey(task));
        queue.add(request);

    }

    @Override
    public int getSurplusRequestsCount(Task task) {
        RQueue<SRequest> queue = redissonClient.<SRequest>getBlockingQueue(getQueueKey(task));
        return queue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getUrlFilter().getRequestCount(task);
    }
}
