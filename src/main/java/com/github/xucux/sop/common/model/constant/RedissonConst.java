package com.github.xucux.sop.common.model.constant;

/**
 * @descriptions: 分布式爬虫相关常量
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface RedissonConst {

    /** 分布式布隆过滤器key */
    String BLOOM_FILTER_PREFIX = "bloom-filter:";

    /** 分布式队列key */
    String QUEUE_LIST_PREFIX = "queue-list:";
}
