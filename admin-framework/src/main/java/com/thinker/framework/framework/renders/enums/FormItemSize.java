package com.thinker.framework.framework.renders.enums;

public enum FormItemSize {
    NORMAL(""), MEDIUM("medium"), SMALL("small"), MINI("mini");

    private final String size;
    FormItemSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return size;
    }
}
