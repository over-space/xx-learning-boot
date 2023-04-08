package com.learning.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author over.li
 * @since 2023/4/5
 */
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class PayApplication{

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
