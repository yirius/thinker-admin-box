package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ImportConfig {

    @ToRenderAttrs
    private String mode = "insert";

    @ToRenderAttrs
    private Boolean message;

    @ToRenderAttrs
    private List<String> types = new ArrayList<String>(){{add("xlsx");add("xls");}};

    @ToRenderAttrs
    private Boolean remote = true;

    @ToRenderAttrs(isEval = true)
    private String importMethod;

    @ToRenderAttrs(isEval = true)
    private String beforeImportMethod;

    @ToRenderAttrs(isEval = true)
    private String afterImportMethod;
}
