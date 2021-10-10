package com.github.xucux.sop.module.core.context;


import com.github.xucux.sop.module.core.downloader.DefaultDownloader;
import com.github.xucux.sop.module.core.downloader.Downloader;
import com.github.xucux.sop.module.core.manager.FilterManager;
import com.github.xucux.sop.module.core.manager.ListenerManager;
import com.github.xucux.sop.module.core.manager.ProcessorManager;
import com.github.xucux.sop.module.core.pipeline.Pipeline;
import com.github.xucux.sop.module.core.repository.Repository;
import com.github.xucux.sop.module.core.repository.impl.DefaultRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions: 配置组件上下文
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class ConfigContext {

    private Downloader downloader;

    private Repository repository;

    protected List<Pipeline> pipelines = new ArrayList<>();

    private ProcessorManager processorManager;

    private FilterManager filterManager;

    private ListenerManager listenerManager;


    /**
     * 初始化管理器
     */
    public ConfigContext() {
        this.processorManager = new ProcessorManager();
        this.filterManager = new FilterManager();
        this.listenerManager = new ListenerManager();
        this.downloader = new DefaultDownloader();
        this.repository = new DefaultRepository();
    }

    public List<Pipeline> getPipelines() {
        return pipelines;
    }

    public ConfigContext setPipelines(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
        return this;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public ConfigContext setDownloader(Downloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public Repository getRepository() {
        return repository;
    }

    public ConfigContext setRepository(Repository repository) {
        this.repository = repository;
        return this;
    }

    public ProcessorManager getProcessorManager() {
        return processorManager;
    }

    public ConfigContext setProcessorManager(ProcessorManager processorManager) {
        this.processorManager = processorManager;
        return this;
    }

    public FilterManager getFilterManager() {
        return filterManager;
    }

    public ConfigContext setFilterManager(FilterManager filterManager) {
        this.filterManager = filterManager;
        return this;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public ConfigContext setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
        return this;
    }
}
