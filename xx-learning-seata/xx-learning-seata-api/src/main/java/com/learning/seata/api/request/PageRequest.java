package com.learning.seata.api.request;

import java.io.Serializable;

/**
 * @author over.li
 * @since 2023/4/4
 */
public class PageRequest implements Serializable {

    private Integer page;

    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
