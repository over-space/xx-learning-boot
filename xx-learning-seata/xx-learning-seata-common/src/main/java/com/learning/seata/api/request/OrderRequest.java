package com.learning.seata.api.request;

import java.io.Serializable;

/**
 * @author over.li
 * @since 2023/4/4
 */
public class OrderRequest implements Serializable {

    private Long goodsBusinessId;

    private Integer shopCount;

    private String description;

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
}
