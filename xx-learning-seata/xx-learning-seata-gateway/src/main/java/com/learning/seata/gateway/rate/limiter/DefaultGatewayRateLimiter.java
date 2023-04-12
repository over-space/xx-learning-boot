package com.learning.seata.gateway.rate.limiter;

import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.alibaba.nacos.shaded.com.google.common.util.concurrent.RateLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author over.li
 * @since 2023/4/11
 */
@Component
@Primary
public class DefaultGatewayRateLimiter extends AbstractRateLimiter<DefaultGatewayRateLimiter.Config> {

    private static final Logger logger = LogManager.getLogger(DefaultGatewayRateLimiter.class);

    private RateLimiter rateLimiter = RateLimiter.create(1);

    /**
     * 和配置文件中的配置属性相对应
     */
    private static final String CONFIGURATION_PROPERTY_NAME = "default-gateway-rate-limiter";

    protected DefaultGatewayRateLimiter(ConfigurationService configurationService) {
        super(DefaultGatewayRateLimiter.Config.class, CONFIGURATION_PROPERTY_NAME, configurationService);
    }


    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        logger.info("网关默认的限流 routeId:[{}],id:[{}]", routeId, id);

        Config config = getConfig().get(routeId);

        return Mono.fromSupplier(() -> {
            boolean acquire = rateLimiter.tryAcquire(config.requestedTokens);
            if (acquire) {
                return new Response(true, Maps.newHashMap());
            } else {
                return new Response(false, Maps.newHashMap());
            }
        });
    }

    public static class Config {

        /**
         * 每次请求多少个 token
         */
        private Integer requestedTokens;

        public Integer getRequestedTokens() {
            return requestedTokens;
        }

        public void setRequestedTokens(Integer requestedTokens) {
            this.requestedTokens = requestedTokens;
        }
    }
}
