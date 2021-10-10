package com.github.xucux.sop.module.core.repository;


import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.module.core.session.SpiderSession;

/**
 * @descriptions: 最终结果仓库
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface Repository {

    /**
     * 存储处理的结果
     * @param session
     * @param resultItems
     */
    void storage(SpiderSession session, ResultItems resultItems);
}
