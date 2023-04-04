package com.learning.seata.service;

/**
 * @author over.li
 * @since 2023/4/4
 */
public interface PayService {

    /**
     * 支付
     * @param orderBusinessId 订单ID
     * @param shopCount 购买数量
     */
    void pay(Long orderBusinessId, int shopCount);
}
