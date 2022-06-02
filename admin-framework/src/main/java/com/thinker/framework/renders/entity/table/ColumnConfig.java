package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ColumnConfig {
    @ToRenderAttrs
    private Boolean useKey;

    @ToRenderAttrs
    private Boolean isCurrent;

    @ToRenderAttrs
    private Boolean isHover;

    @ToRenderAttrs
    private Boolean resizable;

    @ToRenderAttrs
    private String width;

    @ToRenderAttrs
    private String minWidth;
}
