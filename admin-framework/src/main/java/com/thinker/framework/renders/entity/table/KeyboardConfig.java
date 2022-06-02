package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class KeyboardConfig {
    @ToRenderAttrs
    private Boolean isArrow;

    @ToRenderAttrs
    private Boolean isEsc;

    @ToRenderAttrs
    private Boolean isDel;

    @ToRenderAttrs
    private Boolean isEnter;

    @ToRenderAttrs
    private Boolean isTab;

    @ToRenderAttrs
    private Boolean isEdit;

    @ToRenderAttrs
    private Boolean isChecked;

    @ToRenderAttrs
    private Boolean enterToTab;

    @ToRenderAttrs(isEval = true)
    private String delMethod;

    @ToRenderAttrs(isEval = true)
    private String backMethod;

    @ToRenderAttrs(isEval = true)
    private String editMethod;
}
