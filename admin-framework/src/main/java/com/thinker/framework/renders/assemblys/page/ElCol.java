package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElCol extends RootRender {

    @ToRenderAttrs
    private Integer span = 12;

    @ToRenderAttrs
    private Integer offset;

    @ToRenderAttrs
    private Integer push;

    @ToRenderAttrs
    private Integer pull;

    @ToRenderAttrs
    private Integer xs;

    @ToRenderAttrs
    private Integer sm;

    @ToRenderAttrs
    private Integer md;

    @ToRenderAttrs
    private Integer lg;

    @ToRenderAttrs
    private Integer xl;

    @ToRenderAttrs
    private Integer tag;

    @Override
    public void beforeRender() {
        setComponent("ElCol");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
