package com.thinker.framework.framework.database.exceptions;

public class UpdateException extends Exception {

    private int code = 0;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, int code) {
        super(message);
        this.code = code;
    }
}
