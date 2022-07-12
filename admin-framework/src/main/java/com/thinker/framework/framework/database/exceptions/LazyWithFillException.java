package com.thinker.framework.framework.database.exceptions;

public class LazyWithFillException extends Exception{
    private int code = 0;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private String local = null;
    private Object data = null;

    public LazyWithFillException(String local, String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
        this.local = local;
    }

    public LazyWithFillException(String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public LazyWithFillException(String message, int code) {
        super(message);
        this.code = code;
    }

    public LazyWithFillException(String local, String message, int code) {
        super(message);
        this.code = code;
        this.local = local;
    }

    public LazyWithFillException(String message) {
        super(message);
    }
}
