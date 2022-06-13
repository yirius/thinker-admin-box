package com.thinker.framework.framework.aspect.logs;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkLogs;
import com.thinker.framework.admin.serviceimpl.TkLogsImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.token.factory.TokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@Aspect
@Component
public class TableLogAopAdvice {

    public static final String POINTCUT_SIGN =
            "@within(com.thinker.framework.framework.aspect.logs.ThinkerTableLogAspect) || " +
                    "@annotation(com.thinker.framework.framework.aspect.logs.ThinkerTableLogAspect)";

    /**
     * 定义监听注解，进行切面
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {}

    /**
     * 返回结果时
     */
    @AfterReturning(value = "pointcut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        this.writeWarnLog(
                joinPoint, "AfterReturning", JSON.toJSONString(returnValue)
        );
    }

    /**
     * 方法抛出异常
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        this.writeWarnLog(
                joinPoint, "AfterThrowing", JSON.toJSONString(e.getLocalizedMessage())
        );
        e.printStackTrace();
    }

    /**
     * 私有方法，打印一下log
     * @param joinPoint
     * @param type
     * @param msg
     */
    private void writeWarnLog(JoinPoint joinPoint, String type, String msg) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ThinkerTableLogAspect thinkerTableLogAspect = signature.getMethod().getAnnotation(ThinkerTableLogAspect.class);

        Dict paramData = Dict.create();

        HttpServletRequest httpServletRequest = ThinkerAdmin.request().getRequest();
        if(httpServletRequest != null) {
            httpServletRequest.getParameterMap().forEach((s, strings) -> {
                if(!s.toLowerCase().contains("password")) {
                    paramData.set(s, strings[0]);
                }
            });
        }

        String content = "["+joinPoint.getSignature().getDeclaringTypeName()+"."+
                joinPoint.getSignature().getName()+"::"+type+"]["+
                (thinkerTableLogAspect != null ? thinkerTableLogAspect.value() : "")+"]"+
                msg + JSON.toJSONString(paramData);

        TkLogs tkLogs = new TkLogs().setContent(content);
        if(httpServletRequest != null && TokenFactory.loadToken().isLogin()) {
            Dict tokenInfo = TokenFactory.loadToken().checkLogin();
            Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
            int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

            SpringContext.getBean(TkLogsImpl.class).save(
                    tkLogs.setUserId(userId).setUserType(accessType)
            );
        } else {
            SpringContext.getBean(TkLogsImpl.class).save(tkLogs);
        }
    }
}
