package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CheckboxConfig {

    @ToRenderAttrs
    private String labelField = "id";

    @ToRenderAttrs
    private String checkField;

    @ToRenderAttrs
    private Boolean showHeader;

    @ToRenderAttrs
    private Boolean checkAll;

    @ToRenderAttrs
    private List<String> checkRowKeys;

    @ToRenderAttrs
    private Boolean checkStrictly;

    @ToRenderAttrs
    private Boolean strict;

    @ToRenderAttrs
    private Boolean reserve = true;

    @ToRenderAttrs(isEval = true)
    private String visibleMethod;

    @ToRenderAttrs(isEval = true)
    private String checkMethod;

    @ToRenderAttrs
    private String trigger;

    @ToRenderAttrs
    private Boolean highlight = true;

    @ToRenderAttrs
    private Boolean range = true;
}
