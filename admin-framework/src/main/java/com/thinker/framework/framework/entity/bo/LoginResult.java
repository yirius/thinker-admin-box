package com.thinker.framework.framework.entity.bo;

import cn.hutool.core.lang.Dict;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LoginResult {
    private Long userId;
    private String username;
    private String token;
    private int tokenType;
    private Dict data;
}
