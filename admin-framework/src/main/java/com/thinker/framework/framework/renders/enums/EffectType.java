package com.thinker.framework.framework.renders.enums;

public enum EffectType {
    DARK, LIGHT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
