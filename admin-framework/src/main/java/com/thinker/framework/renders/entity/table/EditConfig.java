package com.thinker.framework.renders.entity.table;

import com.thinker.framework.renders.aspects.ToRenderAttrs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EditConfig {

    @ToRenderAttrs
    private String trigger = "click";

    @ToRenderAttrs
    private Boolean enabled;

    @ToRenderAttrs
    private String mode = "cell";

    @ToRenderAttrs
    private Boolean showIcon;

    @ToRenderAttrs
    private Boolean showStatus = true;

    @ToRenderAttrs
    private Boolean showUpdateStatus;

    @ToRenderAttrs
    private Boolean showInsertStatus;

    @ToRenderAttrs
    private Boolean showAsterisk;

    @ToRenderAttrs
    private Boolean autoClear;

    @ToRenderAttrs(isEval = true)
    private String activeMethod;

    @ToRenderAttrs
    private String icon;
}
