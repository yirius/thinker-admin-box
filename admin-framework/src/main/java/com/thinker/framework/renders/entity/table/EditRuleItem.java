package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EditRuleItem {

    @ToRenderAttrs
    private Boolean required;

    @ToRenderAttrs
    private Integer min;

    @ToRenderAttrs
    private Integer max;

    @ToRenderAttrs
    private String type;

    @ToRenderAttrs
    private String pattern;

    @ToRenderAttrs(isEval = true)
    private String validator;

    @ToRenderAttrs
    private String message;

    @ToRenderAttrs
    private String trigger;
}
