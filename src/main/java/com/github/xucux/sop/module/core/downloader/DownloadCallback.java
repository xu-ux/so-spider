package com.github.xucux.sop.module.core.downloader;


import com.github.xucux.sop.module.core.session.SpiderSession;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface DownloadCallback {

    /**
     * http请求成功后执行此方法
     * @param session
     */
    void onSuccess(SpiderSession session);

    /**
     * http请求失败执行此方法，并重试
     * @param session
     * @return
     */
    boolean onFailed(SpiderSession session);

    /**
     * http请求异常执行此方法，并中止
     * @param session
     * @param exception
     */
    void onException(SpiderSession session, Exception exception);

    /**
     * 请求失败后重试
     * @param session
     */
    void retry(SpiderSession session);
}
