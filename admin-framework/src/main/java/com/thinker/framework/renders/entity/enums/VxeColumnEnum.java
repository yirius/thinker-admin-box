package com.thinker.framework.renders.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VxeColumnEnum {
    SEQ("seq"), CHECKBOX("checkbox"), RADIO("radio"), EXPAND("expand"), HTML("html");

    private final String type;
    VxeColumnEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
