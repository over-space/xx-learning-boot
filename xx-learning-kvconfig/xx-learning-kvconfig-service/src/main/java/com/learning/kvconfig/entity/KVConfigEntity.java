package com.learning.kvconfig.entity;

import com.learning.springboot.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author over.li
 * @since 2023/5/18
 */
@Table(name = "t_kv_config")
public class KVConfigEntity extends BaseEntity {

    private String tenantId;

    private String category;

    @Column(nullable = false)
    private String key;

    private String value;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
