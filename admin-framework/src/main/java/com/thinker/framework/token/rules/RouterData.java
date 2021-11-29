package com.thinker.framework.token.rules;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class RouterData {
    // 路由名称，英文
    private String name;

    // 路由对应路径
    private String path;

    // 路由对应的vue文件
    private String component;

    // 路由如果是LAYOUT，指向哪里
    private String redirect;
}
