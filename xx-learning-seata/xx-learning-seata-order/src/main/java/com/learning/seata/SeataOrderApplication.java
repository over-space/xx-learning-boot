package com.learning.seata;

import com.google.common.collect.Lists;
import com.learning.springboot.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author over.li
 * @since 2023/4/5
 */
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class SeataOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication.class, args);
    }



}
