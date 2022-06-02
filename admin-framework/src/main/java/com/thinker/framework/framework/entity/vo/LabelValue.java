package com.thinker.framework.framework.entity.vo;

import cn.hutool.core.lang.Dict;

import java.util.ArrayList;
import java.util.List;

public class LabelValue extends Dict {

    public LabelValue(){}

    public LabelValue(String text, Object value){
        setLabel(text);
        setValue(value);
    }

    public static LabelValue create(String text, Object value) {
        return new LabelValue(text, value);
    }

    public LabelValue setLabel(String text) {
        this.put("label", text);
        return this;
    }

    public String getLabel() {
        return (String) this.getOrDefault("label", null);
    }

    public LabelValue setValue(Object value) {
        this.put("value", value);
        return this;
    }

    public Object getValue() {
        return this.getOrDefault("value", null);
    }

    public LabelValue setChildren(List<LabelValue> children) {
        this.put("children", children);
        return this;
    }

    public List<LabelValue> getChildren() {
        return (List<LabelValue>) this.getOrDefault("children", null);
    }

    public LabelValue set(String attr, Object value) {
        this.put(attr, value);
        return this;
    }

    public LabelValue setIgnoreNull(String attr, Object value) {
        if (null != attr && null != value) {
            this.set(attr, value);
        }

        return this;
    }
}
