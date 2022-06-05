package com.thinker.framework.renders.assemblys.slots;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ElImageSlots {
    @ToRenderAttrs(isSlot = true)
    private RootRender placeholder;

    @ToRenderAttrs(isSlot = true)
    private RootRender error = new ElImageErrorSlot();

    @ToRenderAttrs(isSlot = true)
    private RootRender viewer;
}
