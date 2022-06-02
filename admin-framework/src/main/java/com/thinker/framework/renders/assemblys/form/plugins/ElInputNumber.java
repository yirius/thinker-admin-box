package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.ElSizeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElInputNumber extends FormPluginRender<ElInputNumber> {

    public ElInputNumber() {
        setIsModelValueNumber(true);
    }

    @ToRenderAttrs
    private String placeholder;

    @ToRenderAttrs
    private Integer max;

    @ToRenderAttrs
    private Integer min;

    @ToRenderAttrs
    private Integer step;

    @ToRenderAttrs
    private Boolean stepStrictly;

    @ToRenderAttrs
    private Integer precision;

    @ToRenderAttrs
    private ElSizeEnum size;

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs
    private Boolean controls;

    @ToRenderAttrs
    private String controlsPosition;

    // 设置变更时执行
    @ToRenderAttrs(isEval = true)
    private String onBlur;

    @ToRenderAttrs(isEval = true)
    private String onFocus;

    @ToRenderAttrs(isEval = true)
    private String onChange;

    @Override
    public void beforeRender() {
        setComponent("ElInputNumber");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
