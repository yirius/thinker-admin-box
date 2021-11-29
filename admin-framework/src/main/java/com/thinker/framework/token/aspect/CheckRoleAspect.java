package com.thinker.framework.token.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查用户是否拥有某角色，若拥有1(超级管理员)，则默认覆盖其他角色
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRoleAspect {
    int[] roles();
    boolean isAnd() default true;
}
