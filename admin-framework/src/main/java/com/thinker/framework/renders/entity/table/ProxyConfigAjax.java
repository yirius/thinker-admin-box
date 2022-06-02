package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProxyConfigAjax {

    @ToRenderAttrs(isEval = true)
    private String query;

    @ToRenderAttrs(isEval = true)
    private String queryAll;

    @ToRenderAttrs(isEval = true)
    private String delete;

    @ToRenderAttrs(isEval = true)
    private String save;
}
