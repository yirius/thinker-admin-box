package com.thinker.framework.framework.aspect.logs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RedisLogAopAdvice {

    public static final String POINTCUT_SIGN =
            "@within(com.thinker.framework.framework.aspect.logs.ThinkerRedisLogAspect) || " +
                    "@annotation(com.thinker.framework.framework.aspect.logs.ThinkerRedisLogAspect)";

    /**
     * 定义监听注解，进行切面
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {}

    /**
     * 当进入参数前
     * @param joinPoint
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        Dict paramData = Dict.create();
        ThinkerAdmin.request().getRequest().getParameterMap().forEach((s, strings) -> {
            if(!s.toLowerCase().contains("password")) {
                paramData.set(s, strings[0]);
            }
        });

        this.writeWarnLog(
                joinPoint, "Before", JSON.toJSONString(paramData)
        );
    }

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
        if(ThinkerAdmin.redis().listGetSize("ADMIN_REDIS_LOGS") > 1000) {
            ThinkerAdmin.redis().rm("ADMIN_REDIS_LOGS");
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ThinkerRedisLogAspect thinkerLogAspect = signature.getMethod().getAnnotation(ThinkerRedisLogAspect.class);
        ThinkerAdmin.redis().listSet("ADMIN_REDIS_LOGS", "["+ DateUtil.date().getTime() +"|"+DateUtil.now()+"]["+joinPoint.getSignature().getDeclaringTypeName()+"."+
                joinPoint.getSignature().getName()+"::"+type+"]["+
                (thinkerLogAspect != null ? thinkerLogAspect.value() : "")+"]"+
                msg);
    }
}
