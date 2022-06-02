package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RadioConfig {
    @ToRenderAttrs
    private Boolean strict;

    @ToRenderAttrs
    private Boolean reserve;

    @ToRenderAttrs
    private String labelField;

    @ToRenderAttrs
    private String checkRowKey;

    @ToRenderAttrs(isEval = true)
    private String visibleMethod;

    @ToRenderAttrs(isEval = true)
    private String checkMethod;

    @ToRenderAttrs
    private String trigger;

    @ToRenderAttrs
    private Boolean highlight;
}
