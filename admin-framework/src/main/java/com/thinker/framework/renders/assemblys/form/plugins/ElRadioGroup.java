package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.framework.entity.vo.LabelValue;
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
public class ElRadioGroup extends FormPluginRender<ElRadioGroup> {

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 按钮形式的 Checkbox 激活时的文本颜色
    @ToRenderAttrs
    private String textColor;

    // 按钮形式的 fill 激活时的文本颜色
    @ToRenderAttrs
    private String fill;

    // 设置变更时执行
    @ToRenderAttrs(isEval = true)
    private String onChange;

    public ElRadioGroup addOptions(List<LabelValue> options) {
        getChildren().addAll(options.stream().map(labelValue -> (new ElRadio()).setAttrs(labelValue)).collect(Collectors.toList()));
        return this;
    }

    @Override
    public void beforeRender() {
        setComponent("ElRadioGroup");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
