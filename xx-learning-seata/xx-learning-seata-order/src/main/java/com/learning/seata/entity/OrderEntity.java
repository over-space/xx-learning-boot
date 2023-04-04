package com.learning.seata.entity;

import com.learning.springboot.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author over.li
 * @since 2023/4/4
 */
@Table(name = "t_order")
@Entity
public class OrderEntity extends BaseEntity {

    private Long goodsBusinessId;

    private Integer shopCount;

    private String description;

    private LocalDateTime payTime;

    public Long getGoodsBusinessId() {
        return goodsBusinessId;
    }

    public void setGoodsBusinessId(Long goodsBusinessId) {
        this.goodsBusinessId = goodsBusinessId;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }
}
