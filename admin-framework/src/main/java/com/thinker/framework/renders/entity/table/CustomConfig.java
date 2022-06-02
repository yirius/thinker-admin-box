package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CustomConfig {
    @ToRenderAttrs
    private Boolean storage = true;

    @ToRenderAttrs(isEval = true)
    private String checkMethod;
}
