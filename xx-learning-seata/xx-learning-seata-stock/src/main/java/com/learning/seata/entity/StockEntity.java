package com.learning.seata.entity;

import com.learning.springboot.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 库存表
 * @author over.li
 * @since 2023/4/4
 */
@Table(name = "t_stock")
@Entity
public class StockEntity extends BaseEntity {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 库存总量
     */
    private Integer total;

    /**
     * 已售数据
     */
    private Integer sellCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }
}
