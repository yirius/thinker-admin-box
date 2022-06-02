package com.thinker.framework.renders.entity.form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ElInputAutoSize {
    private Integer minRows;
    private Integer maxRows;
}
