package com.learning.springboot.controller;

import com.learning.springboot.ResponseResult;
import com.learning.springboot.controller.response.HeartbeatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author over.li
 * @since 2023/4/10
 */
@RestController
public class HeartbeatController {

    private final LocalDateTime startupDateTime;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Integer port;

    public HeartbeatController(){
        this.startupDateTime = LocalDateTime.now();
    }

    @GetMapping("/heartbeat")
    public Mono<ResponseResult<HeartbeatResponse>> heartbeat(){
        HeartbeatResponse response = new HeartbeatResponse();
        response.setApplicationName(applicationName);
        response.setServerPort(port);
        response.setStartupDateTime(startupDateTime);
        return Mono.just(ResponseResult.success(String.format("%s service running", applicationName), response));
    }

}
