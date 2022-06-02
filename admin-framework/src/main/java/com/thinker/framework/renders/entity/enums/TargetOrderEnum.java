package com.thinker.framework.renders.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TargetOrderEnum {
    ORIGINAL("original"), PUSH("push"), UNSHIFT("unshift");

    private final String size;
    TargetOrderEnum(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return size;
    }

    @JsonValue
    public String getSize() {
        return size;
    }
}
