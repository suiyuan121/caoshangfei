package com.zj.caoshangfei.common.exceptions;

/**
 * Created by jin.zhang@fuwo.com on 2017/11/10.
 */
public class SystemException extends Exception {

    private String code;

    private String message;

    public SystemException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public SystemException(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
