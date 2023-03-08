package com.learning.plugin.controller.request;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    private int errorCode;

    private String message;

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResponseResult(int errorCode, String message, T data) {
        this(errorCode, message);
        this.data = data;
    }

    public static ResponseResult success(){
        return new ResponseResult(0, "OK");
    }

    public static <T> ResponseResult success(T data){
        return new ResponseResult(0, "OK", data);
    }

    public static <T> ResponseResult success(String message, T data){
        return new ResponseResult(0, message, data);
    }

    public static <T> ResponseResult fail(int errorCode, String message){
        return new ResponseResult(errorCode, message, null);
    }

    public static <T> ResponseResult fail(int errorCode, String message, T data){
        return new ResponseResult(errorCode, message, data);
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
