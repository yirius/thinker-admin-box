package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ExportConfig {

    @ToRenderAttrs
    private String filename;

    @ToRenderAttrs
    private String sheetName;

    @ToRenderAttrs
    private String type = "xlsx";

    @ToRenderAttrs
    private List<String> types;

    @ToRenderAttrs
    private String mode;

    @ToRenderAttrs
    private List<String> modes;

    @ToRenderAttrs
    private Boolean original;

    @ToRenderAttrs
    private Boolean message;

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
    private Boolean download;

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
    private Boolean remote;

    @ToRenderAttrs
    private String style;

    @ToRenderAttrs
    private String useStyle;

    @ToRenderAttrs(isEval = true)
    private String sheetMethod;

    @ToRenderAttrs(isEval = true)
    private String exportMethod;

    @ToRenderAttrs(isEval = true)
    private String beforeExportMethod;

    @ToRenderAttrs(isEval = true)
    private String afterExportMethod;
}
