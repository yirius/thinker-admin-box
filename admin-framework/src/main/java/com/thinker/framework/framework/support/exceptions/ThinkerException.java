package com.thinker.framework.framework.support.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThinkerException extends RuntimeException  {

    private int code = 0;
    private Object data = null;

    public ThinkerException(String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public ThinkerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ThinkerException(String message) {
        super(message);
    }
}
