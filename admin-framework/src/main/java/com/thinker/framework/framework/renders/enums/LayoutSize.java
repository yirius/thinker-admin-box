package com.thinker.framework.framework.renders.enums;

public enum LayoutSize {
    NORMAL(""), MEDIUM("medium"), SMALL("small"), MINI("mini");

    String size;
    LayoutSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return size;
    }
}
