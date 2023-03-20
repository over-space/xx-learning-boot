package com.learning.leetcode.juc;

import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author lifang
 * @since 2022/1/4
 */
public class ThreadPool extends BaseTest {

    @Test
    public void test() {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2,
                10L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

    @Test
    void testParallelStream(){

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "3");

        IntStream.rangeClosed(0, 1000).parallel().forEach(num -> {

            logger.info("num : {}", num);

        });

    }

}
