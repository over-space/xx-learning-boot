package com.learning.seata.api.request;

import java.io.Serializable;

/**
 * @author over.li
 * @since 2023/4/4
 */
public class OrderRequest implements Serializable {

    private Long businessId;

    private Integer shopCount;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }
}
