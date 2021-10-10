package com.github.xucux.sop.module.core.filter;


import com.github.xucux.sop.common.model.request.IRequest;
import com.github.xucux.sop.common.model.request.IResponse;
import com.github.xucux.sop.module.core.session.SpiderSession;

/**
 * HTTP 请求过滤器,可以实现去重策略
 * @author emeory
 */
public interface HttpFilter {

  /**
   * 请求之前过滤方法
   *
   * @param session 会话
   * @param request 将要执行的爬虫请求
   *@return 处理之后的请求, 返回 false 表示取消这个 Request
   */
  boolean beforeRequestFilter(SpiderSession session, IRequest request);

  /**
   * 拿到响应之后的过滤方法
   *
   * @param session 会话
   * @param response HTTP 请求的响应数据
   * @return 如果返回 false, 表示忽略这个响应数据
   */
  boolean afterResponseFilter(SpiderSession session, IResponse response);
}
