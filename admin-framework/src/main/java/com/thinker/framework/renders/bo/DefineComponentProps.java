package com.thinker.framework.renders.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class DefineComponentProps {

    private String type = "Any";

    @JSONField(name = "default")
    private String defaultValue;

    private Boolean required;

    private String validator;
}
