package com.learning.seata.service;

import com.learning.seata.entity.OrderEntity;

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

}
