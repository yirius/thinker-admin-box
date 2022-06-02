package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RowConfig {
    @ToRenderAttrs
    private Boolean useKey;

    @ToRenderAttrs
    private String keyField = "id";

    @ToRenderAttrs
    private Boolean isCurrent;

    @ToRenderAttrs
    private Boolean isHover = true;

    @ToRenderAttrs
    private String height;
}
