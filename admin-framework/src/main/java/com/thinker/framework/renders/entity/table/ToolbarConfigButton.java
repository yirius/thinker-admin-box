package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ToolbarConfigButton {
    @ToRenderAttrs
    private String name;

    @ToRenderAttrs
    private String type;

    @ToRenderAttrs
    private String status;

    @ToRenderAttrs
    private String code;

    @ToRenderAttrs
    private Boolean visible;

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs
    private String icon;

    @ToRenderAttrs
    private Boolean round;

    @ToRenderAttrs
    private Boolean circle;

    @ToRenderAttrs
    private String placement;

    @ToRenderAttrs
    private Boolean destroyOnClose;

    @ToRenderAttrs
    private Boolean transfer;

    @ToRenderAttrs(isJsonObject = true)
    private List<ToolbarConfigButton> dropdowns;

    @ToRenderAttrs(isJsonObject = true)
    private ContentRender buttonRender;
}
