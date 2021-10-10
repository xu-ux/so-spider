package com.github.xucux.sop.module.core.filter.impl;


import com.github.xucux.sop.common.model.constant.RedissonConst;
import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.module.core.filter.UrlFilter;
import com.github.xucux.sop.module.core.task.Task;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;

import java.util.Set;

/**
 * @descriptions: 分布式布隆过滤器
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class RedisBloomFilterRemover implements UrlFilter, RedissonConst {

    /**
     * 预期数据量
     */
    private int insertions;

    /**
     * 误判率
     */
    private double fpp;

    /**
     * redis客户端
     */
    private RedissonClient redissonClient;

    private volatile RBloomFilter<String> bloomFilter;

    public RedisBloomFilterRemover(RedissonClient redissonClient,Task task) {
        this(100000000,0.03,redissonClient,task);
    }

    public RedisBloomFilterRemover(int insertions, double fpp, RedissonClient redissonClient,Task task) {
        this.insertions = insertions;
        this.fpp = fpp;
        this.redissonClient = redissonClient;
        this.bloomFilter = rebuildBloomFilter(task);
    }


    protected RBloomFilter<String> rebuildBloomFilter(Task task) {
        RBloomFilter<String> b = redissonClient.getBloomFilter(getKey(task));
        b .tryInit(insertions,fpp);
        return bloomFilter =  b ;
    }

    private String getKey(Task task){
        return BLOOM_FILTER_PREFIX.concat(task.getTaskId());
    }


    @Override
    public boolean isDuplicate(IRequest request, Task task) {
        boolean isDuplicate = bloomFilter.contains(request.getUrl());
        if (!isDuplicate){
            bloomFilter.add(request.getUrl());
        }
        return isDuplicate;
    }

    @Override
    public void reset(Task task) {
        rebuildBloomFilter(task);
    }

    @Override
    public void reset(Task task, Set<String> urls) {
        rebuildBloomFilter(task);
        urls.stream().forEach(s -> bloomFilter.add(s));
    }

    @Override
    public int getRequestCount(Task task) {
        return (int)bloomFilter.count();
    }
}
