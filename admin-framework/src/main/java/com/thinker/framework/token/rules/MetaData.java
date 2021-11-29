package com.thinker.framework.token.rules;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class MetaData {
    // 路由title  一般必填
    private String title;

    // 图标，也是菜单图标
    private String icon;

    // 内嵌iframe的地址
    private String frameSrc;

    // 当前路由不再标签页显示
    private boolean hideTab;

    // 当前路由不再菜单显示
    private boolean hideMenu;

    // 当前路由不再菜单显示
    private boolean hideClose;
}
