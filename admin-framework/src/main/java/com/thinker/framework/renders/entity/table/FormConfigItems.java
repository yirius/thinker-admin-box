package com.thinker.framework.renders.entity.table;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class FormConfigItems {
    @ToRenderAttrs
    private String field;

    @ToRenderAttrs
    private String title;

    @ToRenderAttrs
    private Integer span;

    @ToRenderAttrs
    private String align;

    @ToRenderAttrs
    private String titleAlign;

    @ToRenderAttrs
    private String titleWidth;

    @ToRenderAttrs
    private Boolean titleOverflow;

    @ToRenderAttrs(isEval = true)
    private String className;

    @ToRenderAttrs
    private Boolean visible;

    @ToRenderAttrs(isEval = true)
    private String visibleMethod;

    @ToRenderAttrs
    private Boolean folding;

    @ToRenderAttrs
    private Boolean collapseNode;

    @ToRenderAttrs(isJsonObject = true)
    private TitlePrefix titlePrefix;

    @ToRenderAttrs(isJsonObject = true)
    private TitlePrefix titleSuffix;

    @ToRenderAttrs
    private Dict resetValue;

    @ToRenderAttrs(isJsonObject = true)
    private EditRender itemRender;

    @ToRenderAttrs(isJsonObject = true)
    private Map<String, List<EditRuleItem>> rules;
}
