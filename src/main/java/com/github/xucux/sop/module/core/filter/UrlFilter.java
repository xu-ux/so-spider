package com.github.xucux.sop.module.core.filter;



import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.module.core.task.Task;

import java.util.Set;

/**
 * @descriptions: url过滤器
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface UrlFilter {

    /**
     * 是否重复
     *
     * @param request 请求信息
     * @param task 任务
     * @return true重复
     */
    boolean isDuplicate(IRequest request, Task task);

    /**
     * 重置布隆过滤器
     */
    void reset(Task task);

    /**
     * 重置布隆过滤器
     * @param task 任务
     * @param urls 需要参与过滤的url地址
     */
    void reset(Task task, Set<String> urls);

    /**
     * 获取不重复的请求总数量
     *
     * @param task 任务
     * @return
     */
    int getRequestCount(Task task);
}
