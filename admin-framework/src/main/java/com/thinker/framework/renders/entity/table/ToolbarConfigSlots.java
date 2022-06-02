package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.ElButton;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ToolbarConfigSlots {
    @ToRenderAttrs(isSlot = true)
    private List<ElButton> buttons = new ArrayList<>();

    @ToRenderAttrs(isSlot = true)
    private List<RootRender> tools;
}
