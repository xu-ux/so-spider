package com.github.xucux.sop.module.core.downloader;


import com.github.xucux.sop.common.config.Option;
import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.common.util.SleepHelper;
import com.github.xucux.sop.module.core.context.SpiderContext;
import com.github.xucux.sop.module.core.listener.Listener;
import com.github.xucux.sop.module.core.pipeline.Pipeline;
import com.github.xucux.sop.module.core.processor.ContentProcessor;
import com.github.xucux.sop.module.core.processor.Processor;
import com.github.xucux.sop.module.core.processor.UrlProcessor;
import com.github.xucux.sop.module.core.session.SpiderSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @descriptions: 下载器回调
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class DefaultDownloadCallback implements DownloadCallback, SleepHelper {

    private static final Logger log = LoggerFactory.getLogger(DefaultDownloadCallback.class);

    private SpiderContext context;

    private Option option;

    private List<Listener> listeners;

    public DefaultDownloadCallback(SpiderContext context) {
        this.context = context;
        this.option = context.getOption();
        this.listeners = context.getListenerManager().getAll();
    }

    @Override
    public void onSuccess(SpiderSession session) {
        Processor processor = session.getProcessor();
        ResultItems resultItems = new ResultItems();
        // 监听器
        listeners.stream().forEach(listener -> listener.onSuccess(session.getRequest()));

        // 处理器
        if (processor instanceof ContentProcessor){
            processor.onSuccess(session, session.getResponse(), resultItems);

        } else if (processor instanceof UrlProcessor){
            // url处理器返回的请求交给上下文并提交给任务队列
            List<SRequest> requests = ((UrlProcessor)processor).onSuccessUrl(session, session.getResponse(), resultItems);
            context.addRequest(requests.toArray(new SRequest[requests.size()]));
        }
        // 默认执行到最后一个ProcessorWrapper交给Pipeline和Repository处理
        if (processor == null || context.getProcessorManager().getLast().getNextWrapper() == null ){
            finalProcess(session,resultItems);
        }
    }

    public void finalProcess(SpiderSession session,ResultItems resultItems){
        if (!resultItems.getSkipPipeline()){
            for (Pipeline pipeline : context.getPipelines()) {
                pipeline.process(resultItems, context);
            }
        }
        context.getRepository().storage(session, resultItems);
    }

    /**
     * http请求失败执行此方法，并重试
     * @param session
     * @return
     */
    @Override
    public boolean onFailed(SpiderSession session) {
        retry(session);
        return true;
    }

    /**
     * http请求异常执行此方法，并中止
     * @param session
     * @param exception
     */
    @Override
    public void onException(SpiderSession session, Exception exception) {
        log.error("发生异常",exception);
        listeners.stream().forEach(listener -> listener.onException(session.getRequest(),exception));
    }

    /**
     * 请求失败后重试
     * @param session
     */
    @Override
    public void retry(SpiderSession session) {
        SRequest request = session.getRequest();
        if (request.getRetryCount() < option.getRetryMax()){
            request.setRetryCount(request.getRetryCount() + 1);
            context.getDownloader().download(session);
            sleep(option.getRetrySleepTime());
        }
    }


}
