package com.github.xucux.sop.module.core.processor.impl;


import com.github.xucux.sop.module.core.processor.Processor;

/**
 * @descriptions: 处理器包装器
 * @author: xucl
 * (@date: 2021/8/d{2})(s*)
 * @version: 1.0
 */
public class ProcessorWrapper {

    /**
     * 处理器class
     */
    private Class<? extends Processor> clazz;
    /**
     * 包装器名称
     */
    private String wrapperName;
    /**
     * 包装的处理器
     */
    private Processor processor;
    /**
     * 下一个包装器
     */
    private ProcessorWrapper nextWrapper;
    /**
     * 上一个包装器
     */
    private ProcessorWrapper preWrapper;

    /**
     * 自定义包装器名称
     * @param wrapperName 包装器名称
     * @param processor 处理器对象
     */
    public ProcessorWrapper(String wrapperName, Processor processor) {
        this.wrapperName = wrapperName;
        this.processor = processor;
        this.clazz = processor.getClass();
    }

    /**
     * 默认包装器名称为时间戳
     * @param processor 处理器对象
     */
    public ProcessorWrapper(Processor processor) {
        this.wrapperName = processor.getClass().getName();
        this.processor = processor;
        this.clazz = processor.getClass();
    }

    public Class getClazz() {
        return clazz;
    }

    public ProcessorWrapper setClazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    public String getWrapperName() {
        return wrapperName;
    }

    public ProcessorWrapper setWrapperName(String wrapperName) {
        this.wrapperName = wrapperName;
        return this;
    }

    public Processor getProcessor() {
        return processor;
    }

    public ProcessorWrapper setProcessor(Processor processor) {
        this.processor = processor;
        return this;
    }

    public ProcessorWrapper getNextWrapper() {
        return nextWrapper;
    }

    public ProcessorWrapper setNextWrapper(ProcessorWrapper nextWrapper) {
        this.nextWrapper = nextWrapper;
        return this;
    }

    public ProcessorWrapper getPreWrapper() {
        return preWrapper;
    }

    public ProcessorWrapper setPreWrapper(ProcessorWrapper preWrapper) {
        this.preWrapper = preWrapper;
        return this;
    }
}
