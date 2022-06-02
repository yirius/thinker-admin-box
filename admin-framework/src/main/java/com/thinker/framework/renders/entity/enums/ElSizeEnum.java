package com.thinker.framework.renders.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ElSizeEnum {
    LARGE("large"), DEFAULT("default"), SMALL("small");

    private final String size;
    ElSizeEnum(String size) {
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
