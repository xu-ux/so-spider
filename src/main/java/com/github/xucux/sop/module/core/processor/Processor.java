package com.github.xucux.sop.module.core.processor;


import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.common.model.request.IResponse;
import com.github.xucux.sop.module.core.session.SpiderSession;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface Processor {

    /**
     * 发出http请求前执行此方法
     * @param session
     * @param request
     */
    default void beforeExecute(SpiderSession session, IRequest request){};

    /**
     * http请求成功后执行此方法
     * @param session
     * @param response
     * @param resultItems
     */
    void onSuccess(SpiderSession session, IResponse response, ResultItems resultItems);

    /**
     * http请求失败执行此方法
     * @param session
     * @param response
     * @return
     */
    boolean onFailed(SpiderSession session, IResponse response);

    /**
     * http请求异常执行此方法
     * @param session
     * @param exception
     */
    void onException(SpiderSession session, Exception exception);


}
