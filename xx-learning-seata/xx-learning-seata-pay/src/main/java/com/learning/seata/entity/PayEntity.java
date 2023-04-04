package com.learning.seata.entity;

import com.learning.springboot.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author over.li
 * @since 2023/4/4
 */
@Table(name = "t_pay")
@Entity
public class PayEntity extends BaseEntity {

    private Long orderBusinessId;

    private BigDecimal originalAmount;

    private BigDecimal discountAmount;

    public Long getOrderBusinessId() {
        return orderBusinessId;
    }

    public void setOrderBusinessId(Long orderBusinessId) {
        this.orderBusinessId = orderBusinessId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
