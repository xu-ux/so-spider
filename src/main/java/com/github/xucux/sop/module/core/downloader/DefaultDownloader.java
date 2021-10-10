package com.github.xucux.sop.module.core.downloader;

import com.github.xucux.sop.common.config.Option;
import com.github.xucux.sop.common.model.constant.HttpConst;
import com.github.xucux.sop.common.model.exception.SpiderException;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.common.model.request.impl.SResponse;
import com.github.xucux.sop.module.core.filter.HttpFilter;
import com.github.xucux.sop.module.core.session.SpiderSession;
import com.github.xucux.sop.module.http.manage.OkHttpHelper;
import okhttp3.Response;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @descriptions: 默认下载器
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class DefaultDownloader implements Downloader, HttpConst {

    private ExecutorService executorService;

    private OkHttpHelper okHttpHelper;

    /** 回调任务处理器 */
    private DownloadCallback downloadCallback;

    /** 异步任务的队列 */
    private LinkedBlockingQueue queue = new LinkedBlockingQueue<>();

    private LinkedList<HttpFilter> httpFilters;

    /** 正在活跃中的任务数量 */
    private AtomicInteger threadAlive = new AtomicInteger();

    @Override
    public DefaultDownloader init(Option option, DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;

        okHttpHelper = OkHttpHelper.proxyBuilder(option);
        executorService = new ThreadPoolExecutor(option.getThread(),
                option.getThread(), 0L, TimeUnit.MILLISECONDS,
                queue, r -> {
            Thread thread = new Thread(r, "S-Thread");
            thread.setDaemon(false);
            return thread;
        });
        return this;
    }

    @Override
    public void setHttpFilters(LinkedList<HttpFilter> httpFilters) {
        this.httpFilters = httpFilters;
    }

    @Override
    public int queueCount(){
        return queue.size();
    }

    @Override
    public int threadAliveCount() {
        return threadAlive.get();
    }

    @Override
    public void download(SpiderSession spiderSession) {
        threadAlive.incrementAndGet();
        executorService.execute(() -> {
            SRequest request = spiderSession.getRequest();
            if (!before(spiderSession)){return;};
            try {
                Response response = okHttpHelper.addRequest(request).send().sync();
                spiderSession.setResponse(new SResponse(response));
                if (!after(spiderSession)){return;};
                if (response.isSuccessful()){
                    downloadCallback.onSuccess(spiderSession);
                } else {
                    downloadCallback.onFailed(spiderSession);
                }
            } catch (SpiderException e) {
                downloadCallback.onException(spiderSession,e);
            } finally {
                threadAlive.incrementAndGet();
            }
        });

    }

    /**
     * 下载器下载前经过过滤器
     * @param spiderSession
     * @return
     */
    private boolean before(SpiderSession spiderSession){
        for (HttpFilter f:httpFilters) {
            boolean b = f.beforeRequestFilter(spiderSession, spiderSession.getRequest());
            if (!b){
                return false;
            }
        }
        return true;
    }

    /**
     * 下载器下载后经过过滤器
     * @param spiderSession
     * @return
     */
    private boolean after(SpiderSession spiderSession){
        for (HttpFilter f:httpFilters) {
            boolean b = f.afterResponseFilter(spiderSession, spiderSession.getResponse());
            if (!b){
                return false;
            }
        }
        return true;
    }
}
