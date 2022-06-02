package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProxyConfigProps {

    @ToRenderAttrs
    private String list;

    @ToRenderAttrs
    private String result = "items";

    @ToRenderAttrs
    private String total = "total";

    @ToRenderAttrs
    private String message = "message";
}
