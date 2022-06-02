package com.thinker.framework.renders.bo.enums;

public enum PropsType {
    String("String"), Number("Number"), Boolean("Boolean"), Array("Array"), Object("Object"), Date("Date"), Function("Function");

    String type;
    PropsType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
