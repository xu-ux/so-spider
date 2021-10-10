package com.github.xucux.sop.module.core.pipeline;


import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.module.core.task.Task;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @descriptions: 打印结果
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public class ConsolePipeline implements Pipeline{

    private static Logger log = LoggerFactory.getLogger(ConsolePipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        log.debug("req url:{}", Joiner.on(",").skipNulls().join(task.getStartUrls()));
    }
}
