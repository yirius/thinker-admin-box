package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ScrollY {
    @ToRenderAttrs
    private Boolean enabled = true;

    @ToRenderAttrs
    private String mode;

    @ToRenderAttrs
    private Integer gt;

    @ToRenderAttrs
    private Integer oSize;
}
