package com.thinker.framework.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.framework.framework.database.entity.ThinkerEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@TableName("tkadmin_rules")
public class TkRules extends ThinkerEntity {

    // 上级菜单
    private Long parentId;

    // 是否主界面
    private Integer isLayout;

    // 是否路由，向前端返回
    private Integer isRouter;

    // 是否直接采用内部组件的json渲染，1是
    private Integer isRender;

    @NotBlank(message = "中文名称必须填写")
    private String title;

    @NotBlank(message = "英文名称必须填写")
    private String titleEn;

    @NotBlank(message = "短参数必须填写")
    private String name;

    private String path;

    private String component;

    // 路由如果是LAYOUT，指向哪里
    private String redirect;

    // 是否持续打开菜单
    private Boolean alwayShow;

    // 图标，也是菜单图标
    private String icon;

    // 图标，也是菜单图标，可以选择手动上传
    private String iconPic;

    // 选中之后反白的图标
    private String iconSelectedPic;

    // 是否缓存
    private Boolean cache;

    // 当前路由不再标签页显示
    private Boolean hideTab;

    // 当前路由不再菜单显示
    private Boolean hideMenu;

    // 是否不显示关闭按钮
    private Boolean hideClose;

    // 权重
    private Long weight;

    // 界面上的内容，只能英文
    private String pageType;
}
