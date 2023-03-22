package com.learning.sharding.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author over.li
 * @since 2022/9/26
 */
@Entity
@Table(name = "t_order_type")
public class OrderTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
