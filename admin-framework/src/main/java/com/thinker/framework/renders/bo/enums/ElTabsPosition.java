package com.thinker.framework.renders.bo.enums;

public enum ElTabsPosition {
    TOP("top"), RIGHT("right"), BOTTOM("bottom"), LEFT("left");

    String type;
    ElTabsPosition(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
