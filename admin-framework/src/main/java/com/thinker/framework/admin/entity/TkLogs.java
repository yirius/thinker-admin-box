package com.thinker.framework.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.framework.framework.database.entity.ThinkerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@TableName("tkadmin_logs")
public class TkLogs extends ThinkerEntity {
    private Long userId;
    private Integer userType;
    private String content;
    private String stages;
    private String typeName;
    private String message;
}
