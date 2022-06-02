package com.thinker.framework.renders.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InputTypeEnum {
    TEXT("text"), TEXTAREA("textarea"), NUMBER("number"), COLOR("color"),
    DATE("date"), EMAIL("email"), FILE("file"), HIDDEN("hidden"), MONTH("month"),
    PASSWORD("password"), RANGE("range"), TEL("tel"), TIME("time"), URL("url"), WEEK("week");

    private final String type;
    InputTypeEnum(String type) {
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
