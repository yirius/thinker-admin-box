package com.thinker.framework.framework.database.exceptions;

public class UpdateException extends Exception {

    private int code = 0;
    private String local = null;
    private Object data = null;

    public UpdateException(String local, String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
        this.local = local;
    }

    public UpdateException(String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public UpdateException(String message, int code) {
        super(message);
        this.code = code;
    }

    public UpdateException(String local, String message, int code) {
        super(message);
        this.code = code;
        this.local = local;
    }

    public UpdateException(String message) {
        super(message);
    }
}
