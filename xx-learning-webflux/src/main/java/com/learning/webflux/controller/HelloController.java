package com.learning.webflux.controller;

import com.learning.webflux.controller.response.UserResponse;
import com.learning.webflux.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author over.li
 * @since 2023/4/7
 */
@RestController
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    @Resource
    private HelloService helloService;

    @GetMapping("/hello")
    public Mono<String> mono(){
        logger.info("{}", "hello");

        return Mono.just(get())
                .cache(Duration.of(5, ChronoUnit.SECONDS))
                .publishOn(Schedulers.boundedElastic())
                .map(s -> helloService.say(s));
    }

    private String get(){
        logger.info("{}", "webflux");
        return "webflux";
    }

}
