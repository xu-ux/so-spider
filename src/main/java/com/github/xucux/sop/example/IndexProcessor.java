package com.github.xucux.sop.example;


import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.common.model.request.IResponse;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.module.core.processor.UrlProcessor;
import com.github.xucux.sop.module.core.session.SpiderSession;

import java.util.List;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/25
 * @version: 1.0
 */
public class IndexProcessor implements UrlProcessor {


    @Override
    public boolean onFailed(SpiderSession session, IResponse response) {
        return false;
    }

    @Override
    public void onException(SpiderSession session, Exception exception) {

    }

    @Override
    public List<SRequest> onSuccessUrl(SpiderSession session, IResponse response, ResultItems resultItems) {
        return null;
    }
}
