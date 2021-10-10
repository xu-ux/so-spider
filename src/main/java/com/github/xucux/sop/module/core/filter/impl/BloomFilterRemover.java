package com.github.xucux.sop.module.core.filter.impl;


import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.module.core.filter.UrlFilter;
import com.github.xucux.sop.module.core.task.Task;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @descriptions: 本地布隆过滤器去重
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class BloomFilterRemover implements UrlFilter {

    /**
     * 记录非重复的url数量
     */
    private AtomicInteger counter;

    /**
     * 预期数据量
     */
    private int insertions;

    /**
     * 误判率
     */
    private double fpp;

    private BloomFilterRemover(int insertions, double fpp) {
        this.insertions = insertions;
        this.fpp = fpp;
        this.bloomFilter = rebuildBloomFilter();
    }

    private BloomFilterRemover(int insertions) {
        this(insertions, 0.01);
    }

    public BloomFilterRemover() {
        this(10000000, 0.01);
    }

    private volatile BloomFilter<CharSequence> bloomFilter;


    protected BloomFilter<CharSequence> rebuildBloomFilter() {
        counter = new AtomicInteger(0);
        return bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), insertions, fpp);
    }

    /**
     * 是否重复
     *
     * @param request 请求信息
     * @param task    任务
     * @return true重复
     */
    @Override
    public boolean isDuplicate(IRequest request, Task task) {
        boolean isDuplicate = bloomFilter.mightContain(request.getUrl());
        if (!isDuplicate){
            bloomFilter.put(request.getUrl());
            counter.incrementAndGet();
        }
        return isDuplicate;
    }

    /**
     * 重置布隆过滤器
     *
     * @param task
     */
    @Override
    public void reset(Task task) {
        rebuildBloomFilter();
    }

    /**
     * 重置布隆过滤器
     *
     * @param task 任务
     * @param urls 需要参与过滤的url地址
     */
    @Override
    public void reset(Task task, Set<String> urls) {
        rebuildBloomFilter();
        urls.stream().forEach(s -> bloomFilter.put(s));
    }

    /**
     * 获取不重复的请求总数量
     *
     * @param task 任务
     * @return
     */
    @Override
    public int getRequestCount(Task task) {
        return counter.get();
    }
}
