package com.learning.seata.service.impl;

import com.learning.seata.entity.OrderEntity;
import com.learning.seata.repository.OrderRepository;
import com.learning.seata.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author over.li
 * @since 2023/4/4
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public void createOrder(OrderEntity order) {
        orderRepository.save(order);
    }
}
