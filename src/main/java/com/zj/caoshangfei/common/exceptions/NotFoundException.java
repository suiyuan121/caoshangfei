package com.zj.caoshangfei.common.exceptions;

/**
 * Created by jin.zhang@fuwo.com on 2017/12/21.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
