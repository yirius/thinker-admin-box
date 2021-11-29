package com.thinker.framework.framework.renders.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TableLayerDataCase {
    private boolean show = false;
    private String title = "数据新增";
    private boolean showButton = true;
}
