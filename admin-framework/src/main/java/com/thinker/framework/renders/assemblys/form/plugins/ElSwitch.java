package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElSwitch extends FormPluginRender<ElSwitch> {

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs(isEval = true)
    private Boolean loading;

    @ToRenderAttrs
    private String size;

    @ToRenderAttrs
    private Integer width;

    @ToRenderAttrs
    private Boolean inlinePrompt;

    @ToRenderAttrs
    private String activeIcon;

    @ToRenderAttrs
    private String inactiveIcon;

    @ToRenderAttrs
    private String activeText;

    @ToRenderAttrs
    private String inactiveText;

    @ToRenderAttrs
    private Integer activeValue = 1;

    @ToRenderAttrs
    private Integer inactiveValue = 0;

    @ToRenderAttrs
    private String activeColor;

    @ToRenderAttrs
    private String inactiveColor;

    @ToRenderAttrs
    private String borderColor;

    // 事件
    @ToRenderAttrs(isEval = true)
    private String beforeChange;

    @ToRenderAttrs(isEval = true)
    private String onChange;

    @Override
    public void beforeRender() {
        setComponent("ElSwitch");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
