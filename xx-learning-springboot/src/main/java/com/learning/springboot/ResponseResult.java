package com.learning.springboot;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ResponseResult<T> implements Serializable {

    private int code;

    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int errorCode, String message, T data) {
        this(errorCode, message);
        this.data = data;
    }

    public static ResponseResult<Void>  success(){
        return new ResponseResult<Void>(0, "OK");
    }

    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult<T>(0, "OK", data);
    }

    public static <T> ResponseResult<T> success(String message, T data){
        return new ResponseResult<T>(0, message, data);
    }

    public static <T> ResponseResult<Void> fail(int errorCode, String message){
        return new ResponseResult<Void>(errorCode, message, null);
    }

    public static <T> ResponseResult<T> fail(int errorCode, String message, T data){
        return new ResponseResult<T>(errorCode, message, data);
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
