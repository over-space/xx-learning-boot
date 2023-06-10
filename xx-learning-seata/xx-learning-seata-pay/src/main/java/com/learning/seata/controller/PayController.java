package com.learning.seata.controller;

import com.learning.springboot.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author over.li
 * @since 2023/4/10
 */
@RestController
public class PayController {

    @GetMapping("/hello")
    public Mono<ResponseResult> hello(){
        return Mono.just(ResponseResult.success());
    }

}
