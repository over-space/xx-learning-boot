package com.learning.springcloud.gateway.route;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author over.li
 * @since 2023/4/13
 */
@Configuration
@Deprecated
public class RouteConfig {

    @Bean
    public RouterFunction<?> helloRouteFunction(){
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.GET("/say/hello"),
                request -> ServerResponse.ok().body(Mono.just("Hello World!"), String.class));;


        for (String path : getPath()) {
            final String url = path.replace("**", "hello");

            route = route.andRoute(
                    RequestPredicates.GET(url),
                    request -> ServerResponse.ok().body(Mono.just("hello " + url), String.class));
        }
        return route;
    }

    public List<String> getPath(){
        return Lists.newArrayList("/menu/**",
                "/vipwxmp/tc/**",
                "/vipwxmp/event/**",
                "/vipwxmp/pz/**",
                "/gift/**",
                "/mp/**",
                "/icrm/**",
                "/act/**",
                "/bk/**",
                "/vipwxmp/me/**",
                "/e/**",
                "/pdmp/**",
                "/wac/**",
                "/nps-crm/**",
                "/harmay-distributor/**",
                "/static/**",
                "/v1/order/pull2",
                "/order/queueSummaryinfo",
                "/branch/query/queueOrderInfo",
                "/queue/config",
                "/datas/**",
                "/datas/**",
                "/vip/**",
                "/data-tools/**",
                "/gift2/**",
                "/report/**",
                "/report/**",
                "/menu/**",
                "/api/vip/transaction",
                "/erp/havi/notify",
                "/erp/havi/**",
                "/order/**",
                "/headquarter/deadLine/**",
                "/statistics/product/**",
                "/erp/ns/**",
                "/import/**",
                "/import/**",
                "/pdmp/**",
                "/pay/h5pay",
                "/pay/h5paystep",
                "/marketing-function-data/new-member/creator",
                "/pay/h5pay",
                "/pay/h5paystep",
                "/headquarter/deadLine/**",
                "/statistics/product/**",
                "/v1/order/pull2",
                "/branch/query/queueOrderInfo",
                "/order/queueSummaryinfo",
                "/queue/config",
                "/menu/**",
                "/act/**",
                "/data-tools/**",
                "/erp/ns/**",
                "/gift/**",
                "/gift2/**",
                "/harmay-distributor/**",
                "/mp/**",
                "/order/**",
                "/pdmp/**",
                "/report/**",
                "/vipwxmp/event/**",
                "/vipwxmp/tc/**",
                "/erp/havi/**",
                "/erp/havi/notify",
                "/menu/**",
                "/report/**",
                "/vip/**",
                "/import/**",
                "/erp/test/havi/notify",
                "/erp/test/havi/notify",
                "/api/vip/transaction",
                "/api/vip/transaction/**",
                "/export/product",
                "/export/product",
                "/oms/**",
                "/oms/**",
                "/api/vip-gift-card/card",
                "/api/printer",
                "/erp/test2/havi/**",
                "/erp/test2/havi/**",
                "/test/gift2/**",
                "/test/gift2/**",
                "/api/marketing-function/group-buy/export",
                "/branch/query/queueOrderInfo",
                "/order/queueSummaryinfo",
                "/order/queueSummaryinfo",
                "/branch/query/queueOrderInfo",
                "/order/queueSummaryinfo",
                "/branch/query/queueOrderInfo",
                "/test/pdmp/**",
                "/test/pdmp/**",
                "/branch-table-code/download",
                "/order-service/**",
                "/order-service/**",
                "/pay/h5pay",
                "/pay/h5paystep",
                "/gift2/**",
                "/gift/**",
                "/vipwxmp/event/**",
                "/activity-record/export",
                "/act/**",
                "/download/coupon-product-excel",
                "/download/coupon-product-to-price-excel",
                "/act/**",
                "/erp/shiji/**",
                "/erp/shiji/**",
                "/act/**",
                "/act/activity-mall/**",
                "/act/mp/activity-mall/**",
                "/e/**",
                "/branch-table-gz-code/download",
                "/report-test/**",
                "/report-test/**",
                "/flipos-invoice/**",
                "/flipos-survey/**",
                "/flipos-member-migration/**",
                "/export/product-sku",
                "/dining/store/sell/air/**",
                "/dining/store/sell/air/**",
                "/dining/store/sell/delivery/**",
                "/dining/store/sell/delivery/**",
                "/import/**",
                "/test/order/**",
                "/test/order/**",
                "/export/product",
                "/flipos-survey/**",
                "/bk2/**",
                "/v1/order/check/**",
                "/v1/order/pull2/**",
                "/dining/store/sell/delivery/**",
                "/dining/store/sell/air/**",
                "/myself/**",
                "/myself/**",
                "/test/bk/**",
                "/api/marketing-function/group-buy/export",
                "/activity-record/export",
                "/download/coupon-product-excel",
                "/download/coupon-product-to-price-excel",
                "/branch-table-gz-code/download",
                "/export/product-sku",
                "/api/order/batch/query",
                "/primary-api/**",
                "/primary-api/**",
                "/primary-api/**",
                "/primary-api/**",
                "/mall/sell/**",
                "/ticket/wxa/**",
                "/act/**",
                "/dining/store/sell/air/**",
                "/dining/store/sell/delivery/**",
                "/wxa/order/coupon/**",
                "/wxa/order/coupons/**",
                "/wx/branch/**");
    }
}
