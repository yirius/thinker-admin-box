package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.select.ElOption;
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
public class ElCheckboxGroup extends FormPluginRender<ElCheckboxGroup> {
    public ElCheckboxGroup() {
        setIsModelValueArray(true);
    }

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 可被勾选的 checkbox 的最小数量
    @ToRenderAttrs(isEval = true)
    private Integer min;

    // 可被勾选的 checkbox 的最大数量
    @ToRenderAttrs(isEval = true)
    private Integer max;

    // 设置 indeterminate 状态，只负责样式控制
    @ToRenderAttrs(isEval = true)
    private Boolean indeterminate;

    // 按钮形式的 Checkbox 激活时的文本颜色
    @ToRenderAttrs
    private String textColor;

    // 按钮形式的 fill 激活时的文本颜色
    @ToRenderAttrs
    private String fill;

    // 设置变更时执行
    @ToRenderAttrs(isEval = true)
    private String onChange;

    public ElCheckboxGroup addOptions(List<LabelValue> options) {
        getChildren().addAll(options.stream().map(labelValue -> (new ElCheckbox()).setAttrs(labelValue)).collect(Collectors.toList()));
        return this;
    }

    @Override
    public void beforeRender() {
        setComponent("ElCheckboxGroup");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
