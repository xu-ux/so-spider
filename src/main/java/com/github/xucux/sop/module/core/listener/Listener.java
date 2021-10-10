package com.github.xucux.sop.module.core.listener;


import com.github.xucux.sop.common.model.request.IRequest;

/**
 * @descriptions: 监听器
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public interface Listener {

    /**
     * 成功监听
     * @param request
     */
    void onSuccess(IRequest request);

    /**
     * 失败监听
     * @param request
     */
    void onException(IRequest request,Exception ex);

}
