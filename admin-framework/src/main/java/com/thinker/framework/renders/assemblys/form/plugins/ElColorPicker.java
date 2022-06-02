package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElColorPicker extends FormPluginRender<ElColorPicker> {

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 是否支持透明度选择
    @ToRenderAttrs(isEval = true)
    private Boolean showAlpha;

    // 是否支持透明度选择
    @ToRenderAttrs(isEval = true)
    private Boolean colorFormat;

    // 预定义颜色
    @ToRenderAttrs(toModelValue = true)
    private List<String> predefine;

    // 设置变更时执行
    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onActiveChange;

    @Override
    public void beforeRender() {
        setComponent("ElColorPicker");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
