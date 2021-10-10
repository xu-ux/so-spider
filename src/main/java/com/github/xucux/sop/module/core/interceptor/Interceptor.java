package com.github.xucux.sop.module.core.interceptor;


import com.github.xucux.sop.common.model.request.impl.SRequest;

/**
 * @descriptions: 发出请求前进入拦截器
 * @author: xucl
 * @date: 2021/8/20
 * @version: 1.0
 */
public interface Interceptor {

    /**
     * 下载器发出http请求前，进行拦截
     *
     * @param request 将要发起的HTTP请求
     * @return true放行 false拦截（不再往下执行）
     */
    boolean beforeDownload(SRequest request);
}
