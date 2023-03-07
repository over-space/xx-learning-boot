package com.learning.plugin.controller.request;

import java.io.Serializable;

public class CommonResponse<T> implements Serializable {

    private int errorCode;

    private String message;

    private T data;

    public CommonResponse() {
    }

    public CommonResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public CommonResponse(int errorCode, String message, T data) {
        this(errorCode, message);
        this.data = data;
    }

    public static CommonResponse success(){
        return new CommonResponse(0, "OK");
    }

    public static <T> CommonResponse success(T data){
        return new CommonResponse(0, "OK", data);
    }

    public static <T> CommonResponse success(String message, T data){
        return new CommonResponse(0, message, data);
    }

    public static <T> CommonResponse fail(int errorCode, String message){
        return new CommonResponse(errorCode, message, null);
    }

    public static <T> CommonResponse fail(int errorCode, String message, T data){
        return new CommonResponse(errorCode, message, data);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
