package com.thinker.framework.framework.entity.vo;

import cn.hutool.core.lang.Dict;

public class TextValue extends Dict {

    public TextValue(){}

    public TextValue(String text, Object value){
        setText(text);
        setValue(value);
    }

    public static TextValue create(String text, Object value) {
        return new TextValue(text, value);
    }

    public TextValue setText(String text) {
        this.put("text", text);
        return this;
    }

    public String getText() {
        return (String) this.getOrDefault("text", null);
    }

    public TextValue setValue(Object value) {
        this.put("value", value);
        return this;
    }

    public Object getValue() {
        return this.getOrDefault("value", null);
    }

    public TextValue set(String attr, Object value) {
        this.put(attr, value);
        return this;
    }

    public TextValue setIgnoreNull(String attr, Object value) {
        if (null != attr && null != value) {
            this.set(attr, value);
        }

        return this;
    }
}
