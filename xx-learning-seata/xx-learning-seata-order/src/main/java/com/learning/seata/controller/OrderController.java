package com.learning.seata.controller;

import com.learning.seata.api.OrderApiFeign;
import com.learning.seata.api.request.OrderRequest;
import com.learning.seata.api.request.PageRequest;
import com.learning.seata.entity.OrderEntity;
import com.learning.seata.service.OrderService;
import com.learning.springboot.ResponseResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author over.li
 * @since 2023/4/4
 */
@RestController
public class OrderController implements OrderApiFeign {

    @Resource
    private OrderService orderService;

    @Override
    public ResponseResult createOrder(OrderRequest request) {
        OrderEntity entity = new OrderEntity();
        entity.setGoodsBusinessId(request.getGoodsBusinessId());
        entity.setShopCount(request.getShopCount());
        entity.setDescription(request.getDescription());
        orderService.createOrder(entity);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Page<OrderEntity>> list(PageRequest request) {
        Page<OrderEntity> list = orderService.list(request);
        return ResponseResult.success(list);
    }
}
