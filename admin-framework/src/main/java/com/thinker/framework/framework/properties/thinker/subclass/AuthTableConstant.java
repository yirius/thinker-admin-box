package com.thinker.framework.framework.properties.thinker.subclass;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthTableConstant {
    String action;
    String secret;
    int expire;
}
