package com.github.xucux.sop.module.core.manager;


import com.github.xucux.sop.module.core.listener.Listener;

import java.util.LinkedList;

/**
 * @descriptions: 监听器管理器
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public class ListenerManager {

    /**
     * 所有的监听器
     */
    private LinkedList<Listener> listeners = new LinkedList<>();

    /**
     * 添加监听器
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public LinkedList<Listener> getAll(){
        return listeners;
    }
}
