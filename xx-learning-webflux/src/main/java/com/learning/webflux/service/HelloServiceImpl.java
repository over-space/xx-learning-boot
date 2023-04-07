package com.learning.webflux.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author over.li
 * @since 2023/4/7
 */
@Service
public class HelloServiceImpl implements HelloService{

    private static final Logger logger = LogManager.getLogger(HelloServiceImpl.class);

    @Override
    public String say(String msg) {
        try {
            logger.info("say: {}", msg);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "hello " + msg;
    }
}
