package com.learning.webflux.controller;

import com.learning.webflux.controller.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author over.li
 * @since 2023/4/6
 */
@RestController
public class UserController {

    @GetMapping("/users/{userId}")
    public Mono<UserResponse> mono(@PathVariable Long userId){
        return Mono.just(new UserResponse(userId, "lisi", "lisi@gmail.com"));
    }

    @GetMapping("/users/list")
    public Flux<UserResponse> flux(){
        return Flux.just(new UserResponse(0L, "lisi", "lisi@gmail.com"));
    }
}
