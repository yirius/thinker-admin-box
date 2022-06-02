package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SortConfig {

    @Data
    public class DefaultSortConfig {
        @ToRenderAttrs
        private String field;
        @ToRenderAttrs
        private String order;
    }

    @ToRenderAttrs(isJsonObject = true)
    private DefaultSortConfig defaultSort;

    @ToRenderAttrs
    private List<String> orders;

    @ToRenderAttrs(isEval = true)
    private String sortMethod;

    @ToRenderAttrs
    private Boolean multiple;

    @ToRenderAttrs
    private Boolean chronological;

    @ToRenderAttrs
    private Boolean remote = true;

    @ToRenderAttrs
    private String trigger = "cell";

    @ToRenderAttrs
    private Boolean showIcon;

    @ToRenderAttrs
    private String iconAsc;

    @ToRenderAttrs
    private String iconDesc;
}
