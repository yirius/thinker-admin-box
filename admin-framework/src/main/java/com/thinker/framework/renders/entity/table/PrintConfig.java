package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PrintConfig {

    @ToRenderAttrs
    private String sheetName = "sheet1";

    @ToRenderAttrs
    private String mode = "current";

    @ToRenderAttrs
    private List<String> modes;

    @ToRenderAttrs
    private Boolean original;

    @ToRenderAttrs
    private Boolean isHeader;

    @ToRenderAttrs
    private Boolean isColgroup;

    @ToRenderAttrs
    private Boolean isFooter;

    @ToRenderAttrs
    private Boolean isMerge;

    @ToRenderAttrs
    private Boolean isAllExpand;

    @ToRenderAttrs
    private List<Object> data;

    @ToRenderAttrs
    private List<String> columns;

    @ToRenderAttrs(isEval = true)
    private String columnFilterMethod;

    @ToRenderAttrs(isEval = true)
    private String dataFilterMethod;

    @ToRenderAttrs(isEval = true)
    private String footerFilterMethod;

    @ToRenderAttrs
    private String style;

    @ToRenderAttrs
    private String useStyle;

    @ToRenderAttrs
    private String content;

    @ToRenderAttrs(isEval = true)
    private String beforePrintMethod;
}
