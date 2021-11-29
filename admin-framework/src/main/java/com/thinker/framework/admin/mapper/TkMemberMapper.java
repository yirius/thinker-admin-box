package com.thinker.framework.admin.mapper;

import com.thinker.framework.admin.entity.TkGroups;
import com.thinker.framework.admin.entity.TkMember;
import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

public interface TkMemberMapper extends ThinkerMapper<TkMember> {

    @Select({"select * from tkadmin_member where id = #{id}"})
    @Results({
            @Result(property = "groups", javaType = TkGroups.class, column = "group_ids",
                    one = @One(select = "com.thinker.backstage.mapper.TkGroupsMapper.selectById", fetchType = FetchType.LAZY)
            )
    })
    TkMember memberWithGroups(@Param("id") Long id);
}
