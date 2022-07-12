package com.thinker.framework.token.aspect;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.TreeNode;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.database.entity.ThinkerEntity;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.utils.CacheUtil;
import com.thinker.framework.token.factory.TokenFactory;
import com.thinker.framework.token.util.ThreadTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class TkTokenAdvice {

    public static final String POINTCUT_SIGN =
            "within(com.thinker.framework.token.extend.ThinkerController+) || " +
            "@within(com.thinker.framework.token.aspect.CheckLoginAspect) || @annotation(com.thinker.framework.token.aspect.CheckLoginAspect) || " +
            "@within(com.thinker.framework.token.aspect.CheckRoleAspect) || @annotation(com.thinker.framework.token.aspect.CheckRoleAspect)";

    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    @Before("pointcut()")
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void before(JoinPoint joinPoint) {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 校验登录
        CheckLoginAspect checkLoginAspect = signature.getMethod().getAnnotation(CheckLoginAspect.class);
        // 校验角色
        CheckRoleAspect checkRoleAspect = signature.getMethod().getAnnotation(CheckRoleAspect.class);
        // 校验规则
        CheckRuleAspect checkRuleAspect = signature.getMethod().getAnnotation(CheckRuleAspect.class);

        Dict tokenInfo = TokenFactory.loadToken().checkLogin();
        Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
        int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

        // 找到角色，然后验证
        if(checkRoleAspect != null) {
            List<String> userRoleIds = ThreadTokenUtil.getUserRolesIds(userId, accessType);
            // 不存在超级管理员
            if(!userRoleIds.contains("1")) {
                boolean isAllRight = true;
                // 如果是or，需要先设置false，在去判断是否存在
                if(!checkRoleAspect.isAnd()) {
                    isAllRight = false;
                }
                for (int i = 0; i < checkRoleAspect.roles().length; i++) {
                    if(checkRoleAspect.isAnd()) {
                        // 因为是and，只需要设置一次false直接退出
                        if(!userRoleIds.contains(String.valueOf(checkRoleAspect.roles()[i]))) {
                            // 如果不存在对应角色
                            isAllRight = false;
                            break;
                        }
                    } else {
                        // 是or，存在任意即可
                        if(userRoleIds.contains(String.valueOf(checkRoleAspect.roles()[i]))) {
                            // 如果不存在对应角色
                            isAllRight = true;
                            break;
                        }
                    }
                }
                if(!isAllRight) {
                    throw new ThinkerException("message.thinker.token.noPermission", "暂无权限访问该模块", 501);
                }
            }
        }

        // 找到规则列表，找到基本路径，然后run一下匹配规则
        if(checkLoginAspect == null && checkRoleAspect == null) {
            List<String> userRuleIds = ThreadTokenUtil.getUserRuleIds(userId, accessType);

            // 规则转化成对应的路径
            List<String> haveRuleComponents = new ArrayList<>();
            CacheUtil.getRuleComponentsMap().forEach((aLong, s) -> {
                if(userRuleIds.contains(aLong)) {
                    if(!haveRuleComponents.contains(s) && Validator.isNotEmpty(s)) {
                        haveRuleComponents.add(s);
                    }
                }
            });

            if(checkRuleAspect != null && Validator.isNotEmpty(checkRuleAspect.name())) {
                // 如果存在name，就直接匹配
                if(!haveRuleComponents.contains(checkRuleAspect.name())) {
                    throw new ThinkerException("message.thinker.token.noPermission", "暂无权限访问该模块", 501);
                }
            } else {
                // 找到基本路径，然后run一下匹配规则
                String currentUrl = ThinkerAdmin.request().getRequest().getServletPath();
                if(currentUrl.toLowerCase().contains(".vue")) {
                    // 如果存在.vue，需要全匹配
                    if(!haveRuleComponents.contains(currentUrl)) {
                        throw new ThinkerException("message.thinker.token.noPermission", "暂无权限访问该模块", 501);
                    }
                } else {
                    // 如果是其他的，需要进行(全/半匹配)
                    if(!haveRuleComponents.contains(currentUrl)) {
                        // 如果全匹配不存在，那就测试半匹配
                        boolean isHalfMatch = false;
                        for (int i = 0; i < haveRuleComponents.size(); i++) {
                            if(currentUrl.contains(haveRuleComponents.get(i))) {
                                isHalfMatch = true;
                                break;
                            }
                        }
                        if(!isHalfMatch) {
                            throw new ThinkerException("message.thinker.token.noPermission", "暂无权限访问该模块", 501);
                        }
                    }
                }
            }
        }
    }
}
