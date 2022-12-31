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
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.utils.CacheUtil;
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

    public static List<String> findUserUrlPageType(Long userId, int accessType, String urlPath) {
        List<String> havePageType = new ArrayList<>();

        if(CacheUtil.getRuleComponentsMap().containsValue(urlPath)) {
            // 找到用户可用的规则id
            List<String> userRuleIds = getUserRuleIds(userId, accessType);
            // 找到所有的规则，进行一次计算
            Map<String, String> componentMap = CacheUtil.getRuleComponentsMap();
            for (String ruleId : componentMap.keySet()) {
                // 如果用户可以适用这个规则，并且规则的component=当前网址，可以找这个规则对应的pageType
                if(userRuleIds.contains(ruleId) && componentMap.get(ruleId).equals(urlPath)) {
                    List<TkRules> tkRulesList = CacheUtil.getAdminRules();
                    // 如果能找到，罗列出来url名下的所有pageType
                    for (int i = 0; i < tkRulesList.size(); i++) {
                        // 如果可以使用这个规则，并且是当前界面
                        if(userRuleIds.contains(tkRulesList.get(i).getId().toString()) &&
                                tkRulesList.get(i).getParentId().equals(Long.parseLong(ruleId))) {
                            if(Validator.isNotEmpty(tkRulesList.get(i).getPageType())) {
                                havePageType.add(tkRulesList.get(i).getPageType());
                            }
                        }
                    }
//                    break;
                }
            }

        }

        return havePageType;
    }
}
