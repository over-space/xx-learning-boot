package com.learning.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author over.li
 * @since 2023/4/6
 */
@EnableWebFlux
@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebfluxApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

}
