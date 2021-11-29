package com.thinker.framework.framework.renders.bo;

public enum PopupPlacement {
    TOP("top"), TOPSTART("top-start"), TOPEND("top-end"),
    BOTTOM("bottom"), BOTTOMSTART("bottom-start"), BOTTOMEND("bottom-end");

    private final String type;
    PopupPlacement(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
