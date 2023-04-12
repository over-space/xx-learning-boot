package com.learning.seata.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author over.li
 * @since 2023/4/5
 */
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class SeataGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataGatewayApplication.class, args);
    }
}
