package com.github.xucux.sop.module.core.session;


import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.common.model.request.impl.SResponse;
import com.github.xucux.sop.module.core.processor.Processor;
import com.github.xucux.sop.module.core.processor.impl.ProcessorWrapper;

/**
 * @descriptions: 会话 一次请求、响应和对应的处理器
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class SpiderSession {

    /**
     * 请求
     */
    private SRequest request;

    /**
     * 响应
     */
    private SResponse response;

    /**
     * 处理器
     */
    private ProcessorWrapper processorWrapper;

    /**
     * 是否跳过此次请求和处理
     */
    private boolean skip = false;

    public SpiderSession(SRequest request, ProcessorWrapper processorWrapper) {
        this.request = request;
        this.processorWrapper = processorWrapper;
    }

    public SpiderSession(SRequest request) {
        this.request = request;
    }

    public SRequest getRequest() {
        return request;
    }

    public SpiderSession setRequest(SRequest request) {
        this.request = request;
        return this;
    }

    public SResponse getResponse() {
        return response;
    }

    public SpiderSession setResponse(SResponse response) {
        this.response = response;
        return this;
    }

    public ProcessorWrapper getProcessorWrapper() {
        return processorWrapper;
    }

    public Processor getProcessor() {
        return processorWrapper.getProcessor();
    }


    public SpiderSession setProcessorWrapper(ProcessorWrapper processorWrapper) {
        this.processorWrapper = processorWrapper;
        return this;
    }

    public boolean isSkip() {
        return skip;
    }

    public SpiderSession setSkip(boolean skip) {
        this.skip = skip;
        return this;
    }
}
