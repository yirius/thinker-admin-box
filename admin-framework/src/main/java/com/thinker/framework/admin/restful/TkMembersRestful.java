package com.thinker.framework.admin.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.admin.entity.TkMember;
import com.thinker.framework.admin.login.AdminLogin;
import com.thinker.framework.admin.mapper.TkMemberMapper;
import com.thinker.framework.admin.serviceimpl.TkMemberImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.aspect.logs.ThinkerTableLogAspect;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import com.thinker.framework.framework.entity.vo.WrapperValue;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.utils.ToolsUtil;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.extend.ThinkerRestful;
import com.thinker.framework.token.factory.TokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
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

        getUseWhere().add(new WrapperValue("username", "like"));
        getUseWhere().add(new WrapperValue("phone", "like"));
        getUseWhere().add(new WrapperValue("status", "eq"));

        setParserWrapper(thinkerWrapper -> {
            Dict tokenInfo = TokenFactory.loadToken().checkLogin();
            Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
            int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

            if(!(accessType == 0 && userId == 1)) {
                thinkerWrapper.ne("id", 1);
            }
        });
    }

    /**
     * 保存
     *
     * @param entity
     * @param bindingResult
     * @return
     */
    @Override
    @ThinkerTableLogAspect
    public ThinkerResponse save(TkMember entity, BindingResult bindingResult) {
        return super.save(entity, bindingResult);
    }

    @Override
    @ThinkerTableLogAspect
    public ThinkerResponse delete(String ids, String password) {
        return super.delete(ids, password);
    }

    @Override
    public Object _afterRead(TkMember entity) {
        Map<String, Object> data = BeanUtil.beanToMap(entity);
        data.put("salt", null);
        data.put("password", null);

        if(Validator.isNotEmpty(entity.getGroupIds())) {
            data.put("groupIds", JSON.parseArray(entity.getGroupIds(), Long.class));
        }
        return data;
    }

    @Override
    public void _beforeSave(TkMember entity) {
        // 判断是否已经存在相同名称的数据
        ThinkerWrapper<TkMember> thinkerWrapper = new ThinkerWrapper<>();
        thinkerWrapper.and(tkMemberQueryWrapper -> {
            tkMemberQueryWrapper.eq("username", entity.getUsername()).or().eq("phone", entity.getPhone());
        });
        if(Validator.isNotEmpty(entity.getId())) { thinkerWrapper.ne("id", entity.getId()); }
        if(getTableImpl().count(thinkerWrapper) > 0) {
            throw new ThinkerException("当前账户名或手机号已存在，请更换");
        }

        if(Validator.isEmpty(entity.getId())) {
            if(Validator.isEmpty(entity.getPassword())) {
                throw new ThinkerException("message.thinker.admin.emptyPassword", "密码为空，请填写", 205);
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
        String redisSuffixName = "";
        // 存在配置
        if(ThinkerAdmin.properties().getToken().getRuleIndex() != null) {
            // 0默认不读取，其他的读取
            if(!(ThinkerAdmin.properties().getToken().getRuleIndex().size() == 1 && ThinkerAdmin.properties().getToken().getRuleIndex().get(0).equals(0))) {
                redisSuffixName = "_" + StrUtil.join("_", ThinkerAdmin.properties().getToken().getRuleIndex());
            }
        }
        // 用户菜单可能变化，直接删除
        ThinkerAdmin.redis().hashDel("ADMIN_ALL_USER_MENUS", entity.getId()+"_0"+redisSuffixName);
        // 用户角色可能变化，直接删除
        ThinkerAdmin.redis().hashDel("ADMIN_USER_ROLES", String.valueOf(entity.getId()));
        // 删除用户信息
        ThinkerAdmin.redis().hashDel("ADMIN_USERS", String.valueOf(entity.getId()), entity.getUsername().toLowerCase(), entity.getPhone());
    }

    @Override
    public List<String> _beforeDelete(String ids) {
        List<String> deleteIds = Arrays.asList(ids.split(","));
        if(deleteIds.contains("1")) {
            throw new ThinkerException("message.thinker.admin.notDelete", "无法删除初始超级管理员", 206, Dict.create().set("reason", "超级管理员id=1"));
        }
        return deleteIds;
    }
}
