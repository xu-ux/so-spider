package com.github.xucux.sop.module.core.pipeline;


import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.module.core.task.Task;

/**
 * @descriptions: 结果处理管道，经过多个管道处理后，再获取结果
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public interface Pipeline {

    /**
     * 处理结果
     * @param resultItems
     * @param task
     */
    void process(ResultItems resultItems, Task task);

}
