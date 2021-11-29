package com.thinker.framework.framework.renders.enums;

public enum FlexJustify {
    START("start"), END("end"), CENTER("center"), SPACEAROUND("space-around"), SPACEBETWEEN("space-between");

    private final String justify;
    FlexJustify(String justify) {
        this.justify = justify;
    }

    @Override
    public String toString() {
        return justify;
    }
}
