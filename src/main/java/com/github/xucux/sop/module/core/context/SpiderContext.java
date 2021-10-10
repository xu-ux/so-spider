package com.github.xucux.sop.module.core.context;


import com.github.xucux.sop.common.config.Option;
import com.github.xucux.sop.common.model.constant.SpiderState;
import com.github.xucux.sop.common.model.exception.SpiderException;
import com.github.xucux.sop.common.model.request.impl.SRequest;
import com.github.xucux.sop.common.util.UrlUtils;
import com.github.xucux.sop.module.core.downloader.DefaultDownloadCallback;
import com.github.xucux.sop.module.core.downloader.DownloadCallback;
import com.github.xucux.sop.module.core.downloader.Downloader;
import com.github.xucux.sop.module.core.listener.impl.MonitorListener;
import com.github.xucux.sop.module.core.pipeline.ConsolePipeline;
import com.github.xucux.sop.module.core.processor.Processor;
import com.github.xucux.sop.module.core.scheduler.SpiderScheduler;
import com.github.xucux.sop.module.core.scheduler.impl.QueueScheduler;
import com.github.xucux.sop.module.core.session.SpiderSession;
import com.github.xucux.sop.module.core.task.Task;
import com.github.xucux.sop.module.server.api.SpiderHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @descriptions: 执行上下文
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class SpiderContext extends ConfigContext implements Runnable, Task {

    protected Logger log = LoggerFactory.getLogger(SpiderContext.class);

    protected volatile SpiderState state = SpiderState.INIT;

    private Option option;

    private String taskId;

    private List<String> startUrls = new ArrayList<>();

    private LocalDateTime startDate;

    protected SpiderScheduler scheduler = new QueueScheduler();

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private DownloadCallback downloadCallback;

    public SpiderContext() {
        super();
    }

    public static SpiderContext create(){
        return new SpiderContext();
    }

    public SpiderContext setOption(Option option){
        this.option = option;
        return this;
    }

    @Override
    public List<String> getStartUrls() {
        return startUrls;
    }

    @Override
    public Option getOption() {
        return option;
    }

    @Override
    public String getTaskId() {
        if (taskId != null) {
            return taskId;
        }
        if (option != null) {
            return option.getDomain();
        }
        taskId = UUID.randomUUID().toString();
        return taskId;
    }

    public SpiderContext setScheduler(SpiderScheduler scheduler) {
        checkIfRunning();
        SpiderScheduler oldScheduler = this.scheduler;
        this.scheduler = scheduler;
        if (oldScheduler != null) {
            SRequest request;
            while ((request = oldScheduler.poll(this)) != null) {
                this.scheduler.push(request, this);
            }
        }
        return this;
    }

    public SpiderContext setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public SpiderContext addUrl(String... urls) {
        for (String url : urls) {
            addRequest(new SRequest(url,getProcessorManager().getFirst().getWrapperName()));
            startUrls.add(url);
        }
        signalNewUrl();
        return this;
    }

    public SpiderContext addUrl(Processor processor,String... urls) {
        for (String url : urls) {
            addRequest(new SRequest(url,processor.getClass().getName()));
            addProcessor(processor);
            startUrls.add(url);
        }
        signalNewUrl();
        return this;
    }

    public void addRequest(SRequest request) {
        if (option.getDomain() == null && request != null && request.getUrl() != null) {
            option.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        scheduler.push(request, this);
    }

    public void addRequest(SRequest... requests) {
        for (SRequest request:requests) {
            if (option.getDomain() == null && request != null && request.getUrl() != null) {
                option.setDomain(UrlUtils.getDomain(request.getUrl()));
            }
            scheduler.push(request, this);
        }
    }

    private void addProcessor(Processor processor){
        getProcessorManager().addProcessor(processor);
    }

    private void initModule(){
        getDownloader().init(option,new DefaultDownloadCallback(this));
        if (pipelines.isEmpty()) {
            pipelines.add(new ConsolePipeline());
        }
        downloadCallback = new DefaultDownloadCallback(this);
        getDownloader().init(option,downloadCallback).setHttpFilters(getFilterManager().getAll());
        getListenerManager().getAll().add(MonitorListener.getInstance());
        // 启动api服务
        SpiderHttpServer.initMonitorServer();
        startDate = LocalDateTime.now();
    }

    /**
     * 分布式场景下，等待首个爬虫爬取
     */
    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            if (getDownloader().queueCount() == 0
                    && getDownloader().threadAliveCount() == 0) {
                return;
            }
            newUrlCondition.await(option.getWaitNewUrlTime(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.warn("waitNewUrl - interrupted, error {}", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    /**
     * 唤醒所有线程
     */
    private void signalNewUrl() {
        try {
            newUrlLock.lock();

            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

    private void checkIfRunning() {
        if (state.equals(SpiderState.RUNNING)) {
            throw new SpiderException("Spider is already running!");
        }
    }

    /**
     * 启动爬虫
     */
    public void start() {
        runAsync();
    }

    /**
     * 启动爬虫
     */
    @Override
    public void run() {
        initModule();
        checkRunningStat();
        log.info("Spider {} started!",getTaskId());
        while (!Thread.currentThread().isInterrupted() && state.equals(SpiderState.RUNNING)) {final SRequest request = scheduler.poll(this);
            Downloader downloader = getDownloader();
            if (request == null) {
                if (downloader.queueCount() == 0
                        && downloader.threadAliveCount() == 0) {
                    break;
                }
                waitNewUrl();
            } else {
                SpiderSession spiderSession = new SpiderSession(request, getProcessorManager().getWrapper(request.getProcessorWrapper()));
                downloader.download(spiderSession);
            }
        }
    }


    private void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    private void checkRunningStat() {
        while (true) {
            if (state == SpiderState.RUNNING) {
                throw new IllegalStateException("Spider is already running!");
            }
            state = SpiderState.RUNNING;
            break;
        }
    }

}
