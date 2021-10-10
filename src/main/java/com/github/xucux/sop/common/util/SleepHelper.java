package com.github.xucux.sop.common.util;

import java.util.Random;

/**
 * @descriptions: 线程休眠工具
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public interface SleepHelper {

    /**
     * 线程休眠
     * @param millis
     */
    default void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 线程休眠随机
     */
    default void sleepRandom() {
        try {
            Thread.sleep((int)(new Random().nextFloat() * 100000));
        } catch (InterruptedException e) {
        }
    }
}
