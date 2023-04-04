package com.learning.seata.service;

/**
 * @author over.li
 * @since 2023/4/4
 */
public interface StockService {

    /**
     * 减库存
     * @param businessId 库存商品业务ID
     * @param count 购买数量
     */
    void subtractStock(Long businessId, int count);

}
