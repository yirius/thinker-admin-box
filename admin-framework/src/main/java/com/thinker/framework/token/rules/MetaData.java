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

    // 图标，也是菜单图标，可以选择手动上传
    private String iconPic;

    // 选中之后反白的图标
    private String iconSelectedPic;

    // 内嵌iframe的地址
    private String frameSrc;

    // 当前路由不再标签页显示
    private boolean hideTab;

    // 当前路由不再菜单显示
    private boolean hideMenu;

    // 当前路由不再菜单显示
    private boolean hideClose;

    // 是否keepalive
    private boolean cache;

    // 是否直接采用内部组件的json渲染，1是
    private boolean isRender;

    // 复写一下组件地址
    private String component;
}
