package com.learning.seata.api;

import com.learning.seata.api.request.OrderRequest;
import com.learning.seata.api.request.PageRequest;
import com.learning.springboot.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author over.li
 * @since 2023/4/4
 */
@FeignClient(value = "xx-learning-seata-order")
public interface OrderApiFeign {

    @PostMapping(value = "/order/create")
    ResponseResult createOrder(@RequestBody OrderRequest request);


    @PostMapping(value = "/order/list")
    ResponseResult list(@RequestBody PageRequest request);
}
