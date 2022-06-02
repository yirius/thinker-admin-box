package com.thinker.framework.renders.entity.table;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FilterRender {
    @ToRenderAttrs
    private String name;

    @ToRenderAttrs
    private Dict props;

    @ToRenderAttrs(isEval = true)
    private String events;

    @ToRenderAttrs
    private String content;
}
