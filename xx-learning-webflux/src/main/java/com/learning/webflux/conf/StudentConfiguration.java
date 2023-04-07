package com.learning.webflux.conf;

import com.learning.springboot.ResponseResult;
import com.learning.webflux.controller.response.UserResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author over.li
 * @since 2023/4/6
 */
@Configuration
public class StudentConfiguration {
    protected static final Logger logger = LogManager.getLogger(StudentConfiguration.class);

    @Bean
    public RouterFunction<ServerResponse> router(StudentHandler handler) {
        return route(GET("/student/{studentId}").and(accept(APPLICATION_JSON)), handler::getStudent)
                .andRoute(GET("/student/list").and(accept(APPLICATION_JSON)), handler::list);
    }


    @Component
    public static class StudentHandler {

        public Mono<ServerResponse> getStudent(ServerRequest request) {
            Long studentId = Long.valueOf(request.pathVariable("studentId"));
            logger.info("studentId: {}", studentId);
            return ServerResponse.ok()
                    .contentType(APPLICATION_JSON)
                    .bodyValue(ResponseResult.success(new UserResponse(studentId, "wangwu", "wangwu@gmail.com")));
        }

        public Mono<ServerResponse> list(ServerRequest request) {
            Long studentId = Long.valueOf(request.pathVariable("studentId"));
            logger.info("studentId: {}", studentId);
            return ServerResponse.ok()
                    .contentType(APPLICATION_JSON)
                    .bodyValue(ResponseResult.success(new UserResponse(studentId, "wangwu", "wangwu@gmail.com")));
        }

    }
}
