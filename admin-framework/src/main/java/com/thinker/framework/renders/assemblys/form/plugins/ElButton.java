package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerHtml;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElButton extends FormPluginRender<ElButton> {
    // 内部文字
    private String label;

    // 按钮尺寸
    @ToRenderAttrs
    private String size;

    // 按钮类型
    @ToRenderAttrs
    private String type = "primary";

    // 是否朴素按钮
    @ToRenderAttrs(isEval = true)
    private Boolean plain;

    // 是否圆角按钮
    @ToRenderAttrs(isEval = true)
    private Boolean round;

    // 是否圆形按钮
    @ToRenderAttrs(isEval = true)
    private Boolean circle;

    // 是否加载中状态
    @ToRenderAttrs(isEval = true)
    private Boolean loading;

    // 自定义加载中图标
    @ToRenderAttrs
    private String loadingIcon;

    // 是否为禁用状态
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 自定义图标
    @ToRenderAttrs
    private String icon;

    // 原生 autofocus 属性
    @ToRenderAttrs(isEval = true)
    private Boolean autofocus;

    // 原生 type 属性
    @ToRenderAttrs
    private String nativeType;

    // 自动在两个中文字符之间插入空格
    @ToRenderAttrs(isEval = true)
    private Boolean autoInsertSpace;

    // 点击事件
    @ToRenderAttrs(isEval = true)
    private String onClick;

    // 是否可以显示
    @ToRenderAttrs(isEval = true)
    private String vIf;

    @Override
    public void beforeRender() {
        getChildren().add(new ThinkerHtml().setHtml(label));
        setComponent("ElButton");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
