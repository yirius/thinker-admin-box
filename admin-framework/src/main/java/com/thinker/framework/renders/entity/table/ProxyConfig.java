package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProxyConfig {

    @ToRenderAttrs
    private Boolean enabled = true;

    @ToRenderAttrs
    private Boolean autoLoad = true;

    @ToRenderAttrs
    private Boolean message = false;

    @ToRenderAttrs
    private Boolean seq = true;

    @ToRenderAttrs
    private Boolean sort = true;

    @ToRenderAttrs
    private Boolean filter = true;

    @ToRenderAttrs
    private Boolean form = true;

    @ToRenderAttrs(isJsonObject = true)
    private ProxyConfigProps props = new ProxyConfigProps();

    @ToRenderAttrs(isJsonObject = true)
    private ProxyConfigAjax ajax;
}
