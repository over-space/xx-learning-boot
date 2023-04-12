package com.learning.seata.gateway.route;

import com.learning.seata.gateway.rate.limiter.DefaultGatewayRateLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author over.li
 * @since 2023/4/10
 */
@Component
public class CustomRouteLocator implements RouteLocator {

    private static final Logger logger = LogManager.getLogger(CustomRouteLocator.class);

    @Resource
    private RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        logger.info("start CustomRouteLocator#getRoutes");
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        builder.route("gateway-test-01", predicateSpec -> {

            /*
             * predicate
             * 1. 创建匹配的断言对应的工厂，例如：yml配置中的path == PathRoutePredicateFactory
             * 2. 通过工厂的newConfig产生对应的配置类对象，这个对象里可以set的属性就是可配置的参数
             */
            PathRoutePredicateFactory pathRoutePredicateFactory = new PathRoutePredicateFactory();

            PathRoutePredicateFactory.Config pathRouteConfig = pathRoutePredicateFactory.newConfig();
            List<String> pattern4StripPrefix = new ArrayList<>();
            pattern4StripPrefix.add("/hello/**");
            pathRouteConfig.setPatterns(pattern4StripPrefix);

            /*
             * filters
             * 1. filters在匹配断言后执行
             * 2. 创建过滤器工厂，例如：yml配置中的StripPrefix == StripPrefixGatewayFilterFactory
             * 3. 通过工厂的newConfig创建配置类，在配置类中给可配置参数赋值
             * */
            StripPrefixGatewayFilterFactory stripPrefixGatewayFilterFactory = new StripPrefixGatewayFilterFactory();
            StripPrefixGatewayFilterFactory.Config stripConfig = stripPrefixGatewayFilterFactory.newConfig();
            stripConfig.setParts(1);
            stripPrefixGatewayFilterFactory.apply(stripConfig);

            /*
             * 组装predicate和filter
             * 1. 即将predicate和filters连接起来
             * 2. 通过PredicateSpec对象的predicate、filters等方法将断言和过滤器等联系起来
             * 3. 内容基本都是：factory.apply(factory.config)
             * */
            predicateSpec.predicate(pathRoutePredicateFactory.apply(pathRouteConfig))
                    .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(stripPrefixGatewayFilterFactory.apply(stripConfig)));
            return predicateSpec.uri("lb://xx-learning-seata-pay");
        });

        builder.route("gateway-test-02", predicateSpec -> {

            BooleanSpec booleanSpec = predicateSpec.predicate(predicate -> {
                // predicate.getAttributes().put("FLIPOS_START_ON", LocalDateTime.now());
                return true;
            });

            boolean isAnd = true;

            if (isAnd) {

                // 通过地址路由
                booleanSpec = booleanSpec.and().path("/hi/**");

                // 通过header路由
                booleanSpec = booleanSpec.and().header("x-header-id", "124|567");

            } else {

                // 通过地址路由
                booleanSpec = booleanSpec.or().path("/hi/**");

                // 通过header路由
                booleanSpec = booleanSpec.or().header("x-header-id", "124|567");


                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                }
            }

            booleanSpec.filters(gatewayFilterSpec -> {
                StripPrefixGatewayFilterFactory stripPrefixGatewayFilterFactory = new StripPrefixGatewayFilterFactory();
                StripPrefixGatewayFilterFactory.Config stripConfig = stripPrefixGatewayFilterFactory.newConfig();
                stripConfig.setParts(1);
                return gatewayFilterSpec.filter(stripPrefixGatewayFilterFactory.apply(stripConfig));
                // return gatewayFilterSpec.filter(stripPrefixGatewayFilterFactory.apply(stripConfig))
                //         .filter((exchange, chain) -> {
                //
                //             ServerHttpRequest request = exchange.getRequest();
                //             List<String> headerAList = request.getHeaders().get("x-header-a");
                //             logger.info("headerAList : {}", headerAList);
                //             if(headerAList == null || headerAList.isEmpty()){
                //                 logger.info("拒绝访问");
                //                 return stop(exchange, HttpStatus.SERVICE_UNAVAILABLE, "拒绝访问");
                //             }
                //             return chain.filter(exchange);
                //         });
            });

            return booleanSpec.uri("lb://xx-learning-seata-pay");
        });

        int value = ThreadLocalRandom.current().nextInt(0, 20);
        if (value < 5) {
            logger.info("模拟动态路由，初始化路由规则：gateway-test-03, value:{}", value);

            builder.route("gateway-test-03", predicateSpec -> {

                BooleanSpec booleanSpec = predicateSpec.predicate(predicate -> true);

                // 通过地址路由
                booleanSpec = booleanSpec.and().path("/welcome/**");

                booleanSpec.filters(gatewayFilterSpec -> {

                    StripPrefixGatewayFilterFactory stripPrefixGatewayFilterFactory = new StripPrefixGatewayFilterFactory();
                    StripPrefixGatewayFilterFactory.Config stripConfig = stripPrefixGatewayFilterFactory.newConfig();
                    stripConfig.setParts(1);

                    return gatewayFilterSpec.filter(stripPrefixGatewayFilterFactory.apply(stripConfig));
                });

                return booleanSpec.uri("lb://xx-learning-seata-pay");
            });
        }

        builder.route("gateway-test-04", predicateSpec -> {

            BooleanSpec booleanSpec = predicateSpec.predicate(predicate -> true);

            // 通过地址路由
            booleanSpec = booleanSpec.and().path("/test/**");

            booleanSpec.filters(gatewayFilterSpec -> {

                StripPrefixGatewayFilterFactory stripPrefixGatewayFilterFactory = new StripPrefixGatewayFilterFactory();
                StripPrefixGatewayFilterFactory.Config stripConfig = stripPrefixGatewayFilterFactory.newConfig();
                stripConfig.setParts(1);

                gatewayFilterSpec = gatewayFilterSpec.requestRateLimiter()
                        .rateLimiter(DefaultGatewayRateLimiter.class, c -> {})
                        .and();

                return gatewayFilterSpec.filter(stripPrefixGatewayFilterFactory.apply(stripConfig));
            });

            return booleanSpec.uri("lb://xx-learning-seata-pay");
        });

        Flux<Route> routes = builder.build().getRoutes();
        logger.info("end CustomRouteLocator#getRoutes");
        return routes;
    }
}
