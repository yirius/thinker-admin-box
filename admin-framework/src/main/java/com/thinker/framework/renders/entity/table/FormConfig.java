package com.thinker.framework.renders.entity.table;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class FormConfig {
    @ToRenderAttrs
    private Dict data;

    @ToRenderAttrs
    private Boolean loading;

    @ToRenderAttrs
    private Integer span;

    @ToRenderAttrs
    private String align;

    @ToRenderAttrs
    private String size;

    @ToRenderAttrs
    private String titleAlign = "right";

    @ToRenderAttrs
    private String titleWidth = "120px";

    @ToRenderAttrs
    private Boolean titleColon;

    @ToRenderAttrs
    private Boolean titleAsterisk;

    @ToRenderAttrs
    private Boolean titleOverflow;

    @ToRenderAttrs(isEval = true)
    private String className;

    @ToRenderAttrs(isJsonObject = true)
    private List<FormConfigItems> items;

    @ToRenderAttrs
    private Boolean collapseStatus;

    @ToRenderAttrs
    private Boolean customLayout;

    @ToRenderAttrs
    private Boolean preventSubmit;

    @ToRenderAttrs
    private ValidConfig validConfig;

    @ToRenderAttrs
    private Boolean enabled;

}
