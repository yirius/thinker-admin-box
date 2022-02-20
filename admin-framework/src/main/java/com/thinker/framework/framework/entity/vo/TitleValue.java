package com.thinker.framework.framework.entity.vo;

import cn.hutool.core.lang.Dict;

public class TitleValue extends Dict {

    public TitleValue(){}

    public TitleValue(String title, Object value){
        setTitle(title);
        setValue(value);
    }

    public static TitleValue create(String title, Object value) {
        return new TitleValue(title, value);
    }

    public TitleValue setTitle(String title) {
        this.put("title", title);
        return this;
    }

    public String getTitle() {
        return (String) this.getOrDefault("title", null);
    }

    public TitleValue setValue(Object value) {
        this.put("value", value);
        return this;
    }

    public Object getValue() {
        return this.getOrDefault("value", null);
    }

    public TitleValue set(String attr, Object value) {
        this.put(attr, value);
        return this;
    }

    public TitleValue setIgnoreNull(String attr, Object value) {
        if (null != attr && null != value) {
            this.set(attr, value);
        }

        return this;
    }
}
