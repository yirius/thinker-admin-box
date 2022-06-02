package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FilterConfig {

    @ToRenderAttrs
    private Boolean remote = true;

    @ToRenderAttrs(isEval = true)
    private String filterMethod;

    @ToRenderAttrs
    private Boolean showIcon;

    @ToRenderAttrs
    private String iconNone;

    @ToRenderAttrs
    private String iconMatch;
}
