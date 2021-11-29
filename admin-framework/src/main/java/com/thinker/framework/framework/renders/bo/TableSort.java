package com.thinker.framework.framework.renders.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TableSort {
    private String prop = "id";
    private String order = "descending";
}
