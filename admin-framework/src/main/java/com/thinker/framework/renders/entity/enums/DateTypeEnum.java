package com.thinker.framework.renders.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DateTypeEnum {
    YEAR("year"), MONTH("month"), DATE("date"),
    DATES("dates"), DATETIME("datetime"), WEEK("week"),
    DATETIMERANGE("datetimerange"), DATERANGE("daterange"), MONTHRANGE("monthrange");

    private final String type;
    DateTypeEnum(String type) {
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
