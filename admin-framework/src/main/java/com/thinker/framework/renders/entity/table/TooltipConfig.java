package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TooltipConfig {
    @ToRenderAttrs
    private Boolean showAll;
    @ToRenderAttrs
    private String theme;
    @ToRenderAttrs
    private Boolean enterable;
    @ToRenderAttrs
    private Integer enterDelay;
    @ToRenderAttrs
    private Integer leaveDelay;
    @ToRenderAttrs(isEval = true)
    private String contentMethod;
}
