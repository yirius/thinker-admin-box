package com.thinker.framework.renders.entity.table;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class ContentRender {
    @ToRenderAttrs
    private String name;

    @ToRenderAttrs
    private Dict props;

    @ToRenderAttrs(isMapEval = true)
    private Map<String, String> events;
}
