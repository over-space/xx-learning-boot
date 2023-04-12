package com.learning.seata.gateway.rate.limiter;

import com.learning.springboot.ResponseResult;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * @author over.li
 * @since 2023/4/12
 */
@Component
@Primary
public class DefaultRequestRateLimiterGatewayFilterFactory extends RequestRateLimiterGatewayFilterFactory {

    public DefaultRequestRateLimiterGatewayFilterFactory(RateLimiter defaultGatewayRateLimiter, KeyResolver defaultGatewayKeyResolver) {
        super(defaultGatewayRateLimiter, defaultGatewayKeyResolver);
    }

    @Override
    public GatewayFilter apply(RequestRateLimiterGatewayFilterFactory.Config config) {
        KeyResolver resolver = getDefaultKeyResolver();
        RateLimiter rateLimiter = getDefaultRateLimiter();

        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

            return resolver.resolve(exchange).flatMap((key) -> {

                return rateLimiter.isAllowed(route.getId(), key).flatMap(response -> {

                    RateLimiter.Response resp = (RateLimiter.Response)response;

                    if (resp.isAllowed()) {
                        return chain.filter(exchange);
                    } else {

                        ServerHttpResponse httpResponse = exchange.getResponse();

                        //修改code为500
                        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                        if (!httpResponse.getHeaders().containsKey("Content-Type")) {
                            httpResponse.getHeaders().add("Content-Type", "application/json");
                        }

                        ResponseResult responseResult = ResponseResult.fail(100, "服务器流量激增，触发限流操作");

                        DataBuffer buffer = httpResponse.bufferFactory().wrap(responseResult.toJSONString().getBytes(StandardCharsets.UTF_8));

                        return httpResponse.writeWith(Mono.just(buffer));
                    }
                });
            });
        };
    }
}
