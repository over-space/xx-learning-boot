package com.learning.seata.service;

import com.learning.seata.api.request.PageRequest;
import com.learning.seata.entity.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author over.li
 * @since 2023/4/4
 */
public interface OrderService {

    /**
     * 创建订单
     * @param order 订单
     */
    void createOrder(OrderEntity order);

    Page<OrderEntity> list(PageRequest request);

}
