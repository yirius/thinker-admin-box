package com.thinker.framework.renders.assemblys.form.plugins.tree;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElTreeProps {

    @ToRenderAttrs
    private String label = "label";

    @ToRenderAttrs
    private String children = "children";

    @ToRenderAttrs
    private String disabled = "disabled";

    @ToRenderAttrs
    private String isLeaf = "isLeaf";
}
