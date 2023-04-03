package com.learning.sharding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
public class XxLearningShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxLearningShardingApplication.class, args);


    }

}
