package com.thinker.framework.renders.assemblys.form.plugins;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.TargetOrderEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElTransfer extends FormPluginRender<ElTransfer> {
    public ElTransfer() {
        setIsModelValueArray(true);
    }

    @ToRenderAttrs(toModelValue = true)
    private List<LabelValue> data;

    @ToRenderAttrs
    private Boolean filterable;

    @ToRenderAttrs
    private String filterPlaceholder;

    @ToRenderAttrs(isEval = true)
    private String filterMethod;

    @ToRenderAttrs
    private TargetOrderEnum targetOrder;

    @ToRenderAttrs(toModelValue = true)
    private List<String> titles;

    @ToRenderAttrs(toModelValue = true)
    private List<String> buttonTexts;

    @ToRenderAttrs(isEval = true)
    private String renderContent;

    @ToRenderAttrs(toModelValue = true)
    private Dict format;

    @ToRenderAttrs(toModelValue = true)
    private Dict props = Dict.create().set("key", "value").set("label", "label").set("disabled", "disabled");

    @ToRenderAttrs(toModelValue = true)
    private List<Object> leftDefaultChecked;

    @ToRenderAttrs(toModelValue = true)
    private List<Object> rightDefaultChecked;

    //事件
    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onLeftCheckChange;

    @ToRenderAttrs(isEval = true)
    private String onRightCheckChange;

    @ToRenderAttrs(isSlot = true)
    private String defaultTpl;

    @ToRenderAttrs(isSlot = true)
    private String leftFooter;

    @ToRenderAttrs(isSlot = true)
    private String rightFooter;

    @Override
    public void beforeRender() {
        setComponent("ElTransfer");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
