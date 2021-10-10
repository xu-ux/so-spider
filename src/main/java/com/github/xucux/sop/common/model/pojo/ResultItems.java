package com.github.xucux.sop.common.model.pojo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public class ResultItems {

    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    /**
     * 是否跳过管道处理器
     */
    private boolean skipPipeline;

    public boolean isEmpty(){
        return data.isEmpty();
    }

    public <T> T get(String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        return (T) data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ResultItems setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public <T> ResultItems put(String key, T value) {
        data.put(key, value);
        return this;
    }

    public boolean getSkipPipeline() {
        return skipPipeline;
    }

    public ResultItems setSkipPipeline(boolean skipPipeline) {
        this.skipPipeline = skipPipeline;
        return this;
    }
}
