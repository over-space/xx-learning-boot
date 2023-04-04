package com.learning.seata.service.impl;

import com.learning.seata.entity.GoodsEntity;
import com.learning.seata.repository.GoodsRepository;
import com.learning.seata.service.GoodsService;
import com.learning.springboot.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author over.li
 * @since 2023/4/4
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    /**
     * 减库存
     *
     * @param businessId 库存商品业务ID
     * @param count      购买数量
     */
    @Override
    public void subtractStock(Long businessId, int count) {
        GoodsEntity goodsEntity = goodsRepository.findOneByBusinessId(businessId);
        if(goodsEntity == null){
            throw new BusinessException(1000, "不存在的商品");
        }

        // 库存
        Integer total = goodsEntity.getTotal();
        Integer sellCount = goodsEntity.getSellCount();

        if(total == null || total <= 0 || (total - count) < 0){
            throw new BusinessException(1001, "库存不足");
        }

        // 更新库存信息
        goodsEntity.setTotal(total - count);
        goodsEntity.setSellCount(sellCount == null ? count : sellCount + count);
        goodsRepository.save(goodsEntity);
    }
}
