package com.thinker.framework.renders.entity;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.entity.vo.LabelValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class RenderResult {

    // 参数不能直接修改，只能修改中内容
    private String component;

    @Setter(AccessLevel.NONE)
    private Dict attrs = Dict.create();

    @Setter(AccessLevel.NONE)
    private List<RenderResult> children = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    private Map<String, List<RenderResult>> slots = new HashMap<>();

    @Setter(AccessLevel.NONE)
    private Dict extInfo = Dict.create();

    public RenderResult(){}

    public RenderResult(String component){
        setComponent(component);
    }

    public RenderResult(String component, Dict attrs){
        setComponent(component);
        this.attrs.putAll(attrs);
    }

    public RenderResult(String component, Dict attrs, List<RenderResult> children){
        setComponent(component);
        this.attrs.putAll(attrs);
        this.children.addAll(children);
    }

    public RenderResult(String component, Dict attrs, List<RenderResult> children, Map<String, List<RenderResult>> slots){
        setComponent(component);
        this.attrs.putAll(attrs);
        this.slots.putAll(slots);
        this.children.addAll(children);
    }

    public static RenderResult create(String component) {
        return new RenderResult(component);
    }

    public static RenderResult create(String component, Dict attrs) {
        return new RenderResult(component, attrs);
    }

    public static RenderResult create(String component, Dict attrs, List<RenderResult> children) {
        return new RenderResult(component, attrs, children);
    }

    public static RenderResult create(String component, Dict attrs, List<RenderResult> children, Map<String, List<RenderResult>> slots) {
        return new RenderResult(component, attrs, children, slots);
    }
}
