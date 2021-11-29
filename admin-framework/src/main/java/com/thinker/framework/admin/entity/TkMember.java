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
@TableName("tkadmin_member")
public class TkMember extends ThinkerEntity {

    @NotBlank(message = "账户名必须填写")
    private String username;

    @NotBlank(message = "手机号必须填写")
    private String phone;

    @NotBlank(message = "真实姓名必须填写")
    private String realname;

    /**
     * 登录的密码
     */
    private String password;
    private String salt;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 设置组别ID
     */
    private String groupIds;

    /**
     * 状态
     */
    private Integer status;
}
