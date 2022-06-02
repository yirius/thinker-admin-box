package com.thinker.framework.renders.bo.enums;

public enum ElTabsType {
    DEFAULT(null), CARD("card"), BORDERCARD("border-card");

    String type;
    ElTabsType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
