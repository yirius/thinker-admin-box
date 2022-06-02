package com.thinker.framework.renders.assemblys.form.plugins;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.ElSizeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElSlider extends FormPluginRender<ElSlider> {

    public ElSlider() {
        setIsModelValueNumber(true);
    }

    @ToRenderAttrs
    private Integer min;

    @ToRenderAttrs
    private Integer max;

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs
    private Integer step;

    @ToRenderAttrs
    private Boolean showInput;

    @ToRenderAttrs
    private Boolean showInputControls;

    @ToRenderAttrs
    private ElSizeEnum size;

    @ToRenderAttrs
    private ElSizeEnum inputSize;

    @ToRenderAttrs
    private Boolean showStops;

    @ToRenderAttrs
    private Boolean showTooltip;

    @ToRenderAttrs(isEval = true)
    private String formatTooltip;

    @ToRenderAttrs
    private Boolean range;

    @ToRenderAttrs
    private Boolean vertical;

    @ToRenderAttrs
    private Integer height;

    @ToRenderAttrs
    private Integer debounce;

    @ToRenderAttrs
    private String tooltipClass;

    @ToRenderAttrs(toModelValue = true)
    private Dict marks;

    // 事件
    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onInput;

    @Override
    public void beforeRender() {
        setComponent("ElSlider");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
