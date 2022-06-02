package com.thinker.framework.renders.entity.page;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LayerBoxProps {
    @ToRenderAttrs
    private Boolean show = false;

    @ToRenderAttrs
    private String title = "弹出界面";

    @ToRenderAttrs
    private Boolean showButton = true;

    @ToRenderAttrs
    private String renderUrl;
}
