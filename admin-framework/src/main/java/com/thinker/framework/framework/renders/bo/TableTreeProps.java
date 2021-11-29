package com.thinker.framework.framework.renders.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TableTreeProps {
    private String hasChildren = "hasChildren";
    private String children = "children";
}
