package com.learning.seata.service.impl;

import com.learning.seata.api.OrderApiFeign;
import com.learning.seata.service.PayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author over.li
 * @since 2023/4/4
 */
@Service
public class PayServiceImpl implements PayService {


    @Resource
    private OrderApiFeign orderApiFeign;

    /**
     * 支付
     *
     * @param orderBusinessId 订单ID
     * @param shopCount       购买数量
     */
    @Override
    public void pay(Long orderBusinessId, int shopCount) {
    }
}
