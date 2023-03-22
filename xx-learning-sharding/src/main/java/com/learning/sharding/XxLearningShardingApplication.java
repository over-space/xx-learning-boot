package com.learning.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class XxLearningShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxLearningShardingApplication.class, args);
    }

}
