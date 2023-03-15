package com.learning.plugin.controller.request;

import java.io.Serializable;

/**
 * @author over.li
 * @since 2023/3/13
 */
public class BaseRequest implements Serializable {

    private Long businessId;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
