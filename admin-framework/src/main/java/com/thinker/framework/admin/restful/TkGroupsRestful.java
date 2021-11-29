package com.thinker.framework.admin.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.admin.entity.TkGroups;
import com.thinker.framework.admin.mapper.TkGroupsMapper;
import com.thinker.framework.admin.serviceimpl.TkGroupsImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.aspect.logs.ThinkerRedisLogAspect;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.token.extend.ThinkerRestful;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/restful/thinker/system/roles")
public class TkGroupsRestful extends ThinkerRestful<TkGroupsMapper, TkGroups> {

    public TkGroupsRestful() {
        setUseTable(TkGroupsImpl.class);
    }

    @Override
    public Object _afterRead(TkGroups entity) {
        Map<String, Object> map = BeanUtil.beanToMap(entity);
        map.put("ruleIds", Validator.isEmpty(entity.getRuleIds()) ? new JSONArray() :
                Arrays.stream(entity.getRuleIds().split(",")).map(Long::parseLong).collect(Collectors.toList()));
        return map;
    }

    @Override
    public void _afterSave(TkGroups entity, boolean isUpdate) {
        // 用户菜单可能变化，直接删除
        ThinkerAdmin.redis().rm("ADMIN_ALL_USER_MENUS");
        // 用户角色可能变化，直接删除
        ThinkerAdmin.redis().rm("ADMIN_USER_ROLES");
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
