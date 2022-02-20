package com.thinker.framework.framework.renders.enums;

public enum LayoutSize {
    NORMAL(""), MINI("mini"), SMALL("small"), MEDIUM("medium"), LARGE("large");

    String size;
    LayoutSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return size;
    }
}
