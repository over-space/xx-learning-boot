package com.learning.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author over.li
 * @since 2022/9/21
 */
public abstract class BaseTest implements Serializable {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(32),
            new ThreadFactory() {
                public AtomicInteger count = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(String.format("text-thread-%2d", count.incrementAndGet()));
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy());

    private static void loadLoggerConfig() {
        try {
            LogManager.setFactory(new Log4jContextFactory());
            ConfigurationSource configurationSource = ConfigurationSource.fromResource("log4j2.xml", BaseTest.class.getClassLoader());
            Configurator.initialize(null, configurationSource);
        } catch (Exception e) {
            logger.error("加载log4j2.xml配置失败...., error:{}", e.getMessage());
        }

    }

    @BeforeAll
    public static void beforeAll() {
        loadLoggerConfig();
        logger.info("================================================================================================");
        logger.info("-------------------------------------开始执行测试方法---------------------------------------------");
        logger.info("");
    }

    @AfterAll
    public static void afterAll() {
        logger.info("");
        logger.info("-------------------------------------测试方法执行完成---------------------------------------------");
        logger.info("================================================================================================");
    }

    protected <T> CompletableFuture runAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            return supplier.get();
        }, threadPoolExecutor);
    }

    protected static void line() {
        logger.info("************************************************************************************************");
    }

    protected void print(String msg) {
        logger.info("input : {}", msg);
    }

    protected static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected static String getResourcePath(Class clazz, String resource){
        return clazz.getClassLoader().getResource(resource).getPath();
    }
}
