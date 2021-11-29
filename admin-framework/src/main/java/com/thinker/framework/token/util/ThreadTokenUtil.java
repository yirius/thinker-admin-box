package com.thinker.framework.token.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.utils.ToolsUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ThreadTokenUtil {

    /**
     * 设置角色和规则在本次请求中的参数
     * @param userId
     * @param accessType
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setRolesAndRules(Long userId, int accessType) {
        LoginAbstract loginAbstract = LoginFactory.loadLogin(accessType);
        List<TextValue> textValues = loginAbstract.getUserRoles(loginAbstract.getUser(userId));
        List<String> ruleIdsList = new ArrayList<>();
        List<String> roleIdsList = new ArrayList<>();
        if(textValues != null) {
            textValues.forEach(textValue -> {
                roleIdsList.add(String.valueOf(textValue.getValue()));
                ruleIdsList.addAll(ToolsUtil.strToList(textValue.getStr("rules")));
            });
        }

        ThinkerAdmin.thread().setObject("RULES_" + userId + "_" + accessType, ruleIdsList);
        ThinkerAdmin.thread().setObject("ROLES_" + userId + "_" + accessType, roleIdsList);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getUserRuleIds(Long userId, int accessType) {
        List<String> ruleIdsList = (List<String>) ThinkerAdmin.thread().getObject("RULES_" + userId + "_" + accessType);

        if(ruleIdsList == null) {
            setRolesAndRules(userId, accessType);

            ruleIdsList = (List<String>) ThinkerAdmin.thread().getObject("RULES_" + userId + "_" + accessType);
        }

        return ruleIdsList;
    }

    @SuppressWarnings("unchecked")
    public static List<String> getUserRolesIds(Long userId, int accessType) {
        List<String> rolesIdsList = (List<String>) ThinkerAdmin.thread().getObject("ROLES_" + userId + "_" + accessType);

        if(rolesIdsList == null) {
            setRolesAndRules(userId, accessType);

            rolesIdsList = (List<String>) ThinkerAdmin.thread().getObject("ROLES_" + userId + "_" + accessType);
        }

        return rolesIdsList;
    }
}
