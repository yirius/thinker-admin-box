package com.thinker.framework.framework.database.exceptions;

public class LazyWithFillException extends Exception{
    private int code = 0;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public LazyWithFillException(String message) {
        super(message);
    }

    public LazyWithFillException(String message, int code) {
        super(message);
        this.code = code;
    }
}
