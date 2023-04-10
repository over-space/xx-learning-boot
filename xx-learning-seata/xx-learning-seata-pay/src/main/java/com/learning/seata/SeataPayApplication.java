package com.learning.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author over.li
 * @since 2023/4/5
 */
@EnableWebFlux
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.learning.seata", "com.learning.springboot"})
public class SeataPayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SeataPayApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }
}
