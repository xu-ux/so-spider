package com.github.xucux.sop.module.core.task;


import com.github.xucux.sop.common.config.Option;

import java.util.List;

/**
 * @descriptions: 请求任务
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface Task {

    /**
     * 任务id
     *
     * @return
     */
    String getTaskId();


    Option getOption();


    List<String> getStartUrls();
}
