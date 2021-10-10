package com.github.xucux.sop.module.core.repository.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.module.core.repository.Repository;
import com.github.xucux.sop.module.core.session.SpiderSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/25
 * @version: 1.0
 */
public class DefaultRepository implements Repository {

    private static final Logger log = LoggerFactory.getLogger(DefaultRepository.class);

    @Override
    public void storage(SpiderSession session, ResultItems resultItems) {
        log.info("repository result:{}", JSONObject.toJSONString(resultItems));
    }
}
