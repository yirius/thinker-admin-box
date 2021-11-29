package com.thinker.framework.framework.renders.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TablePageCase {
    private Integer index = 1;
    private Integer size = 20;
    private Integer total = 0;
    private String sort;
    private String order;
}
