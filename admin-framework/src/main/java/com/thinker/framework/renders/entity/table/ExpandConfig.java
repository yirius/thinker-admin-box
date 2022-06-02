package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ExpandConfig {

    @ToRenderAttrs
    private String labelField;

    @ToRenderAttrs
    private Boolean expandAll;

    @ToRenderAttrs
    private List<String> expandRowKeys;

    @ToRenderAttrs
    private Boolean accordion;

    @ToRenderAttrs
    private String trigger;

    @ToRenderAttrs
    private Boolean lazy;

    @ToRenderAttrs(isEval = true)
    private String loadMethod;

    @ToRenderAttrs(isEval = true)
    private String toggleMethod;

    @ToRenderAttrs(isEval = true)
    private String visibleMethod;

    @ToRenderAttrs
    private Boolean reserve;

    @ToRenderAttrs
    private Boolean showIcon;

    @ToRenderAttrs
    private String iconOpen;

    @ToRenderAttrs
    private String iconClose;

    @ToRenderAttrs
    private String iconLoaded;
}
