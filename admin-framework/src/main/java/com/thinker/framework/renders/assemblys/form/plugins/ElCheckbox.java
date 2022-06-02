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
public class ElCheckbox extends FormPluginRender<ElCheckbox> {
    // 选中状态的值
    @ToRenderAttrs
    private String label;

    // 选中时的值
    @ToRenderAttrs
    private String trueLabel;

    // 选中时的值
    @ToRenderAttrs
    private String falseLabel;

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 是否显示边框
    @ToRenderAttrs(isEval = true)
    private Boolean border;

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 当前是否勾选
    @ToRenderAttrs(isEval = true)
    private Boolean checked;

    // 设置 indeterminate 状态，只负责样式控制
    @ToRenderAttrs(isEval = true)
    private Boolean indeterminate;

    // 设置变更时执行
    @ToRenderAttrs(isEval = true)
    private String onChange;

    private Boolean isButton = false;

    @Override
    public void beforeRender() {
        setComponent("ElCheckbox"+(isButton?"Button":""));
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
