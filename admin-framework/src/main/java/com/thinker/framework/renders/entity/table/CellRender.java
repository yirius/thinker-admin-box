package com.thinker.framework.renders.entity.table;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CellRender {
    @ToRenderAttrs
    private String name;

    @ToRenderAttrs
    private Dict props;

    @ToRenderAttrs
    private List<LabelValue> options;

    @ToRenderAttrs
    private Dict optionProps;

    @ToRenderAttrs
    private List<LabelValue> optionGroups;

    @ToRenderAttrs
    private Dict events;

    @ToRenderAttrs
    private String content;

    @ToRenderAttrs
    private List<Dict> children;
}
