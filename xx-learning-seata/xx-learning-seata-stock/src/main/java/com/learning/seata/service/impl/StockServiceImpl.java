package com.learning.seata.service.impl;

import com.learning.seata.entity.StockEntity;
import com.learning.seata.repository.StockRepository;
import com.learning.seata.service.StockService;
import com.learning.springboot.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author over.li
 * @since 2023/4/4
 */
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockRepository stockRepository;

    /**
     * 减库存
     *
     * @param businessId 库存商品业务ID
     * @param count      购买数量
     */
    @Override
    public void subtractStock(Long businessId, int count) {
        StockEntity stockEntity = stockRepository.findOneByBusinessId(businessId);
        if(stockEntity == null){
            throw new BusinessException(1000, "不存在的商品");
        }

        // 库存
        Integer total = stockEntity.getTotal();
        Integer sellCount = stockEntity.getSellCount();

        if(total == null || total <= 0 || (total - count) < 0){
            throw new BusinessException(1001, "库存不足");
        }

        // 更新库存信息
        stockEntity.setTotal(total - count);
        stockEntity.setSellCount(sellCount == null ? count : sellCount + count);
        stockRepository.save(stockEntity);
    }
}
