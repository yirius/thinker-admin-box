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
@TableName("tkadmin_groups")
public class TkGroups extends ThinkerEntity {
    @NotBlank(message = "组别名称必须填写")
    private String title;

    @NotBlank(message = "组别对应英文代号必须填写")
    private String name;

    private String ruleIds;

    private Integer status;

    // 权限类型
    private Integer accessType;

    // 用户编号
    private Long memberId;
}
