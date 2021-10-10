package com.github.xucux.sop.module.core.listener.impl;


import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.module.core.listener.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @descriptions: 监控监听器
 * @author: xucl
 * @date: 2021/8/25
 * @version: 1.0
 */
public class MonitorListener implements Listener {

    private static MonitorListener instance = new MonitorListener();

    private MonitorListener (){}

    public static MonitorListener getInstance() {
        return instance;
    }

    private final AtomicInteger successCount = new AtomicInteger(0);

    private final AtomicInteger errorCount = new AtomicInteger(0);


    private List<String> errorUrls = Collections.synchronizedList(new ArrayList<String>());


    @Override
    public void onSuccess(IRequest request) {
        successCount.incrementAndGet();
    }

    @Override
    public void onException(IRequest request,Exception ex) {
        errorUrls.add(request.getUrl());
        errorCount.incrementAndGet();
    }

    public AtomicInteger getSuccessCount() {
        return successCount;
    }

    public AtomicInteger getErrorCount() {
        return errorCount;
    }

    public List<String> getErrorUrls() {
        return errorUrls;
    }
}
