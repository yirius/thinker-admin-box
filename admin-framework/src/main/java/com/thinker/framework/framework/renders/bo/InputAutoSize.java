package com.thinker.framework.framework.renders.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InputAutoSize {
    private Integer minRows = 2;
    private Integer maxRows = 6;
}
