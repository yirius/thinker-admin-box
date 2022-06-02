package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TreeConfig {

    @ToRenderAttrs
    private Boolean transform;

    @ToRenderAttrs
    private String rowField;

    @ToRenderAttrs
    private String parentField;

    @ToRenderAttrs
    private String children = "children";

    @ToRenderAttrs
    private Integer indent = 20;

    @ToRenderAttrs
    private Boolean line;

    @ToRenderAttrs
    private Boolean expandAll = true;

    @ToRenderAttrs
    private List<String> expandRowKeys;

    @ToRenderAttrs
    private Boolean accordion;

    @ToRenderAttrs
    private String trigger;

    @ToRenderAttrs
    private Boolean lazy;

    @ToRenderAttrs
    private String hasChild;

    @ToRenderAttrs(isEval = true)
    private String loadMethod;

    @ToRenderAttrs(isEval = true)
    private String toggleMethod;

    @ToRenderAttrs
    private Boolean reserve;

    @ToRenderAttrs
    private Boolean showIcon = true;

    @ToRenderAttrs
    private String iconOpen;

    @ToRenderAttrs
    private String iconClose;

    @ToRenderAttrs
    private String iconLoaded;
}
