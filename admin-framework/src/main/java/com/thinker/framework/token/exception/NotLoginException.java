package com.thinker.framework.token.exception;

import com.thinker.framework.framework.support.exceptions.ThinkerException;

public class NotLoginException extends ThinkerException {

    public NotLoginException(String local, String message, int code, Object data) {
        super(local, message, code, data);
    }

    public NotLoginException(String message, int code, Object data) {
        super(message, code, data);
    }

    public NotLoginException(String message, int code) {
        super(message, code);
    }

    public NotLoginException(String local, String message, int code) {
        super(local, message, code);
    }

    public NotLoginException(String message) {
        super(message);
    }
}
