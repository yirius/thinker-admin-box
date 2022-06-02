package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PagerConfig {
    @ToRenderAttrs
    private List<String> layouts;

    @ToRenderAttrs
    private Integer currentPage;

    @ToRenderAttrs
    private Integer pageSize = 20;

    @ToRenderAttrs
    private Integer total;

    @ToRenderAttrs
    private Integer pagerCount;

    @ToRenderAttrs
    private List<Integer> pageSizes = Arrays.asList(20, 50, 100, 500, 1000, 3000);

    @ToRenderAttrs
    private String align;

    @ToRenderAttrs
    private Boolean border;

    @ToRenderAttrs
    private Boolean background;

    @ToRenderAttrs
    private Boolean perfect;

    @ToRenderAttrs(isEval = true)
    private String className;

    @ToRenderAttrs
    private Boolean autoHidden;

    @ToRenderAttrs
    private Boolean enabled;
}
