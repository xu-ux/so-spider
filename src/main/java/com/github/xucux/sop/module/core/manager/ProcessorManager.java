package com.github.xucux.sop.module.core.manager;

import com.github.xucux.sop.module.core.processor.Processor;
import com.github.xucux.sop.module.core.processor.impl.ProcessorWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

/**
 * @descriptions: 处理器管理者
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class ProcessorManager {

    /**
     * 所有的处理器（顺序排列的存储'处理器'容器）
     */
    private LinkedList<ProcessorWrapper> wrappers = new LinkedList<>();


    /**
     * 添加处理器
     * @param processor 新增加的处理器
     * @return 返回处理器名称
     */
    public String addProcessor(Processor processor) {
        ProcessorWrapper wrapper = new ProcessorWrapper(processor);
        if (getSize() > 0){
            ProcessorWrapper last = getLast();
            last.setNextWrapper(wrapper);
            wrapper.setPreWrapper(last);
        }
        wrappers.addLast(wrapper);
        return wrapper.getWrapperName();
    }

    /**
     * 添加处理器
     * @param wrapperName 包装器名称
     * @param processor 新增加的处理器
     * @return 返回处理器名称
     */
    public String addProcessor(String wrapperName,Processor processor) {
        ProcessorWrapper wrapper = new ProcessorWrapper(wrapperName,processor);
        if (getSize() > 0){
            ProcessorWrapper last = getLast();
            last.setNextWrapper(wrapper);
            wrapper.setPreWrapper(last);
        }
        wrappers.addLast(wrapper);
        return wrapperName;
    }

    /**
     * 根据包装器名称获取指定包装器
     * @param wrapperName
     * @return
     */
    public ProcessorWrapper getWrapper(String wrapperName) {
        if (StringUtils.isBlank(wrapperName)) {
//            throw new SpiderException("wrapperName is not be null!");
            return null;
        }
        return wrappers.stream().filter(w -> w.getWrapperName().equals(wrapperName)).findFirst().orElse(null);
    }

    /**
     * 根据class对象获取指定的包装器
     * @param clazz 处理器的clazz
     * @return
     */
    public ProcessorWrapper getWrapper(Class<? extends Processor> clazz) {
        return wrappers.stream().filter(w -> clazz.isAssignableFrom(w.getClazz())).findFirst().orElse(null);
    }


    public int getSize(){
        return wrappers.size();
    }

    public ProcessorWrapper getLast(){
        return wrappers.getLast();
    }

    public ProcessorWrapper getFirst(){
        return wrappers.getFirst();
    }

}
