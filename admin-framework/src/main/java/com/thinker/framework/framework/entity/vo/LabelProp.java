package com.thinker.framework.framework.entity.vo;

import cn.hutool.core.lang.Dict;

public class LabelProp extends Dict {

    public LabelProp(){}

    public LabelProp(String text, Object prop){
        setLabel(text);
        setProp(prop);
    }

    public static LabelProp create(String text, Object prop) {
        return new LabelProp(text, prop);
    }

    public LabelProp setLabel(String text) {
        this.put("label", text);
        return this;
    }

    public String getLabel() {
        return (String) this.getOrDefault("label", null);
    }

    public LabelProp setProp(Object prop) {
        this.put("prop", prop);
        return this;
    }

    public Object getProp() {
        return this.getOrDefault("prop", null);
    }

    public LabelProp set(String attr, Object prop) {
        this.put(attr, prop);
        return this;
    }

    public LabelProp setIgnoreNull(String attr, Object prop) {
        if (null != attr && null != prop) {
            this.set(attr, prop);
        }

        return this;
    }
}
