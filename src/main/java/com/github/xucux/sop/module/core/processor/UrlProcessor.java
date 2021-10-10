package com.github.xucux.sop.module.core.processor;

import com.github.xucux.sop.common.model.exception.SpiderException;
import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.common.model.request.IResponse;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.session.SpiderSession;

import java.util.List;

/**
 * @descriptions: 地址处理器
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface UrlProcessor extends Processor {

    /**
     * http请求成功后执行此方法
     * @param session
     * @param response
     * @param resultItems
     */
    @Deprecated
    @Override
    default void onSuccess(SpiderSession session, IResponse response, ResultItems resultItems){
        throw new SpiderException("错误的执行方法");
    }

    /**
     * http请求成功后执行此方法返回下一个需要执行的url
     * @param session
     * @param response
     * @param resultItems
     * @return
     */
    List<SRequest> onSuccessUrl(SpiderSession session, IResponse response, ResultItems resultItems);

}
