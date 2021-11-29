package com.thinker.framework.framework.renders.enums;

public enum LayoutLargeSize {
    LARGE("large"), MEDIUM("medium"), SMALL("small"), MINI("mini");

    private final String size;
    LayoutLargeSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return size;
    }
}
