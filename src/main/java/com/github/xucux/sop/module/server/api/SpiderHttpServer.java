package com.github.xucux.sop.module.server.api;

import com.github.xucux.sop.module.server.api.handler.MonitorHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @descriptions: 监控服务接口
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class SpiderHttpServer {

    private static final int PORT = 26081;

    private static final Logger log = LoggerFactory.getLogger(SpiderHttpServer.class);

 /*   static {
        initMonitorServer();
    }*/

    public synchronized static void initMonitorServer(){
        //创建一个HttpServer实例，并绑定到指定的IP地址和端口号

        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(PORT);
            HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);
            //创建一个HttpContext，将路径为/monitor请求映射到SpiderHttpHandler处理器
            httpServer.createContext("/monitor", new MonitorHandler());

            //设置服务器的线程池对象
            httpServer.setExecutor(Executors.newFixedThreadPool(2));

            //启动服务器
            httpServer.start();
            log.info("SpiderHttpServer Start Success Port:{} !",PORT);
            log.info("Monitor Address: http://localhost:{}/monitor",PORT);
        } catch (Exception e) {
            log.error("SpiderHttpServer Start Error !",e);
        }
    }
}
