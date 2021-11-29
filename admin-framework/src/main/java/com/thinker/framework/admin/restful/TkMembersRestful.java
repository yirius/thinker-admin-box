package com.thinker.framework.admin.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.admin.entity.TkMember;
import com.thinker.framework.admin.login.AdminLogin;
import com.thinker.framework.admin.mapper.TkMemberMapper;
import com.thinker.framework.admin.serviceimpl.TkMemberImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.utils.ToolsUtil;
import com.thinker.framework.token.extend.ThinkerRestful;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/restful/thinker/system/members")
public class TkMembersRestful extends ThinkerRestful<TkMemberMapper, TkMember> {

    public TkMembersRestful() {
        setUseTable(TkMemberImpl.class);
    }

    @Override
    public Object _afterRead(TkMember entity) {
        Map<String, Object> data = BeanUtil.beanToMap(entity);
        data.put("salt", null);
        data.put("password", null);

        List<Long> groupIds = new ArrayList<>();
        if(Validator.isNotEmpty(data.get("groupIds"))) {
            String[] strings = String.valueOf(data.get("groupIds")).split(",");
            for (int i = 0; i < strings.length; i++) {
                groupIds.add(Long.parseLong(strings[i]));
            }
        }
        data.put("groupIds", groupIds);
        return data;
    }

    @Override
    public void _beforeSave(TkMember entity) {
        if(Validator.isEmpty(entity.getId())) {
            if(Validator.isEmpty(entity.getPassword())) {
                throw new ThinkerException("message.thinker.admin.emptyPassword", 205);
            }
        }

        if(Validator.isNotEmpty(entity.getPassword())) {
            entity.setSalt(ToolsUtil.rand(6, ToolsUtil.RandFormat.NUMBER));
            entity.setPassword((new AdminLogin()).createPassword(entity.getPassword(), entity));
        } else {
            entity.setPassword(null);
        }

        super._beforeSave(entity);
    }

    @Override
    public void _afterSave(TkMember entity, boolean isUpdate) {
        // 用户菜单可能变化，直接删除
        ThinkerAdmin.redis().hashDel("ADMIN_ALL_USER_MENUS", entity.getId()+"_0");
        // 用户角色可能变化，直接删除
        ThinkerAdmin.redis().hashDel("ADMIN_USER_ROLES", String.valueOf(entity.getId()));
        // 删除用户信息
        ThinkerAdmin.redis().hashDel("ADMIN_USERS", String.valueOf(entity.getId()), entity.getUsername().toLowerCase(), entity.getPhone());
    }

    @Override
    public List<String> _beforeDelete(String ids) {
        List<String> deleteIds = Arrays.asList(ids.split(","));
        if(deleteIds.contains("1")) {
            throw new ThinkerException("message.thinker.admin.notDelete|{\"reason\":\"超级管理员id=1\"}", 206);
        }
        return deleteIds;
    }
}
