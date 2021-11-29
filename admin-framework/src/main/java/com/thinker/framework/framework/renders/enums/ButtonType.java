package com.thinker.framework.framework.renders.enums;

public enum ButtonType {
    NORMAL(""), PRIMARY("primary"), SUCCESS("success"), WARNING("warning"), DANGER("danger"), INFO("info"), TEXT("text");

    String type;
    ButtonType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
