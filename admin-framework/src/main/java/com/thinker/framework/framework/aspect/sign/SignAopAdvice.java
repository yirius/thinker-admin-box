package com.thinker.framework.framework.aspect.sign;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.aspect.logs.ThinkerLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SignAopAdvice {

    public static final String POINTCUT_SIGN =
            "@within(com.thinker.framework.framework.aspect.sign.ThinkerSignAspect) || " +
                    "@annotation(com.thinker.framework.framework.aspect.sign.ThinkerSignAspect)";

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
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ThinkerSignAspect thinkerSignAspect = signature.getMethod().getAnnotation(ThinkerSignAspect.class);
        ThinkerAdmin.properties().getToken().getSignAspectUseType(thinkerSignAspect.signIndex()).verifySign();
    }

    /**
     * 返回结果时
     */
    @AfterReturning(value = "pointcut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ThinkerSignAspect thinkerSignAspect = signature.getMethod().getAnnotation(ThinkerSignAspect.class);
        ThinkerAdmin.properties().getToken().getSignAspectUseType(thinkerSignAspect.signIndex()).parseReturn(returnValue);
    }
}
