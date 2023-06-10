package com.learning.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author over.li
 * @since 2023/4/5
 */
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class SeataGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataGoodsApplication.class, args);
    }
}
