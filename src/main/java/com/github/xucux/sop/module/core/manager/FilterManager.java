package com.github.xucux.sop.module.core.manager;


import com.github.xucux.sop.module.core.filter.HttpFilter;

import java.util.LinkedList;

/**
 * @descriptions: 过滤器管理者
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class FilterManager {

    /**
     * 所有的http过滤器
     */
    private LinkedList<HttpFilter> filters = new LinkedList<>();

    /**
     * 添加监听器
     */
    public void addFilters(HttpFilter filter) {
        filters.add(filter);
    }

    public LinkedList<HttpFilter> getAll(){
        return filters;
    }
}
