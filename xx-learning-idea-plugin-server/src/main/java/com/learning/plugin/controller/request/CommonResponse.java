package com.learning.plugin.controller.request;

import java.io.Serializable;

public class CommonResponse<T> implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
