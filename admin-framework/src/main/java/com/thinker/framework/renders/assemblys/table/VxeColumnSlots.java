package com.thinker.framework.renders.assemblys.table;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class VxeColumnSlots {

    @ToRenderAttrs(isSlot = true)
    private List<RootRender> defaultTpl = new ArrayList<>();

    @ToRenderAttrs(isSlot = true)
    private String header;

    @ToRenderAttrs(isSlot = true)
    private String footer;

    @ToRenderAttrs(isSlot = true)
    private String title;

    @ToRenderAttrs(isSlot = true)
    private String checkbox;

    @ToRenderAttrs(isSlot = true)
    private String radio;

    @ToRenderAttrs(isSlot = true)
    private String content;

    @ToRenderAttrs(isSlot = true)
    private String filter;

    @ToRenderAttrs(isSlot = true)
    private String edit;
}
