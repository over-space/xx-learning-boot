package com.learning.seata.gateway.rate.limiter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author over.li
 * @since 2023/4/12
 */
@Component
public class DefaultGatewayKeyResolver implements KeyResolver {

    private static final Logger logger = LogManager.getLogger(DefaultGatewayKeyResolver.class);

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 获取当前路由
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getPath();
        logger.info("当前返回的uri:[{}]", uri);

        return Mono.just( Optional.ofNullable(route).map(Route::getId).orElse("") + "/" + uri);
    }
}
