package com.github.xucux.sop.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @descriptions: redis客户端初始化配置
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class RedissonConfig {

    private static final Logger log = LoggerFactory.getLogger(RedissonConfig.class);

    /**
     * 单节点模式
     * @param address
     * @param password
     * @param database
     * @return
     */
    public RedissonClient init(String address,String password,int database) {
        try {
            Config config = new Config();
            config.useSingleServer().setAddress(address).setPassword(password).setDatabase(database);
            RedissonClient redisson = Redisson.create(config);
            return redisson;
        } catch (Exception e) {
            log.error("connect redis single server error! ",e);
            return null;
        }
    }

    /**
     * 集群模式
     * @param address
     * @return
     */
    public RedissonClient init(String... address){
        try {
            Config config = new Config();
            config.useClusterServers()
                    //设置集群状态扫描时间
                    .setScanInterval(2000)
                    //设置连接数
                    .setMasterConnectionPoolSize(10000)
                    .setSlaveConnectionPoolSize(10000)
                    .addNodeAddress(address)
                    //同任何节点建立连接时的等待超时。时间单位是毫秒。默认：10000
                    .setConnectTimeout(30000)
                    //等待节点回复命令的时间。该时间从命令发送成功时开始计时。默认:3000
                    .setTimeout(10000)
                    //如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时。默认值：3
                    .setRetryAttempts(5)
                    //在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。     默认值：1500
                    .setRetryInterval(3000);
            RedissonClient redisson = Redisson.create(config);
            return redisson;
        } catch (Exception e) {
            log.error("connect redis cluster server error! ",e);
            return null;
        }
    }
}
