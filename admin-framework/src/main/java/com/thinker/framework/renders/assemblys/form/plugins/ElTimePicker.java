package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElTimePicker extends FormPluginRender<ElTimePicker> {

    @ToRenderAttrs(isEval = true)
    private Boolean readonly;

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean editable;

    @ToRenderAttrs
    private Boolean clearable;

    @ToRenderAttrs
    private String placeholder;

    @ToRenderAttrs
    private String startPlaceholder;

    @ToRenderAttrs
    private String endPlaceholder;

    @ToRenderAttrs
    private Boolean isRange;

    @ToRenderAttrs
    private Boolean arrowControl;

    @ToRenderAttrs
    private String align;

    /**
     * DatePicker 下拉框的类名
     */
    @ToRenderAttrs
    private String popperClass;

    /**
     * 选择范围时的分隔符
     */
    @ToRenderAttrs
    private String rangeSeparator;

    /**
     * 可选，选择器打开时默认显示的时间
     */
    @ToRenderAttrs(isEval = true)
    private String defaultValue;

    /**
     * 可选，范围选择时选中日期所使用的当日内具体时刻
     */
    @ToRenderAttrs(isEval = true)
    private String defaultTime;

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

    /**
     * 一个用来判断该日期是否被禁用的函数，接受一个 Date 对象作为参数 ，应该返回一个 Boolean 值 Should return a Boolean
     */
    @ToRenderAttrs(isEval = true)
    private String disabledHours;

    @ToRenderAttrs(isEval = true)
    private String disabledMinutes;

    @ToRenderAttrs(isEval = true)
    private String disabledSeconds;

    @ToRenderAttrs
    private String format;

    @ToRenderAttrs
    private String valueFormat = "HH:mm:ss";

    // 事件
    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onBlur;

    @ToRenderAttrs(isEval = true)
    private String onFocus;

    @ToRenderAttrs(isEval = true)
    private String onVisibleChange;

    @Override
    public void beforeRender() {
        setComponent("ElTimePicker");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
