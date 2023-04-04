package com.learning.springboot.exception;

/**
 * @author over.li
 * @since 2023/4/4
 */
public class BusinessException extends RuntimeException{

    private int code;

    private String message;

    public BusinessException(String message) {
        this(0, message);
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
