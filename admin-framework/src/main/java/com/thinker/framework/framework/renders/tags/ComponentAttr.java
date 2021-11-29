package com.thinker.framework.framework.renders.tags;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentAttr {
    String prevStr() default "";
    boolean isRef() default false;
    boolean isReactive() default false;
    boolean isEvent() default false;
    String eventArgs() default "";
    // 在触发事件的时候，是否需要指定v-show="test()"类型，或许需要
    String eventStateArgs() default "";
}
