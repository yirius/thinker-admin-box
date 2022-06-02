package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.ElSizeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElTimeSelect extends FormPluginRender<ElTimeSelect> {

    @ToRenderAttrs
    private Boolean disabled;

    /**
     * 文本框可输入
     */
    @ToRenderAttrs
    private Boolean editable;

    /**
     * 	是否显示清除按钮
     */
    @ToRenderAttrs
    private Boolean clearable;

    @ToRenderAttrs
    private ElSizeEnum size;

    @ToRenderAttrs
    private String placeholder;

    @ToRenderAttrs
    private String effect;

    /**
     * 自定义前缀图标
     */
    @ToRenderAttrs
    private String prefixIcon;

    /**
     * 清楚日期图标
     */
    @ToRenderAttrs
    private String clearIcon;

    @ToRenderAttrs
    private String start;

    @ToRenderAttrs
    private String end;

    @ToRenderAttrs
    private String step;

    @ToRenderAttrs
    private String minTime;

    @ToRenderAttrs
    private String maxTime;

    @ToRenderAttrs
    private String format;

    // 事件
    @ToRenderAttrs
    private String onChange;

    @ToRenderAttrs
    private String onBlur;

    @ToRenderAttrs
    private String onFocus;

    @Override
    public void beforeRender() {
        setComponent("ElTimeSelect");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
