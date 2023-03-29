package com.learning.sharding.entity;

import com.learning.springboot.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author over.li
 * @since 2022/9/20
 */
@Entity
@Table(name = "t_order_item")
public class OrderItemEntity extends BaseEntity {

    private Long orderId;

    private Integer orderType;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}