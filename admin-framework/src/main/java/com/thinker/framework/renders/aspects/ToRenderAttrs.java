package com.thinker.framework.renders.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToRenderAttrs {
    // 是否需要重新渲染eval
    boolean isEval() default false;
    // 是否是模型值
    boolean isModelRefsValue() default false;
    // 是否是提交的值，form值
    boolean isFormValue() default false;
    // 是否使用对应值填充formValue
    boolean isFormValueField() default false;
    // 将值转入modelValue
    boolean toModelValue() default false;
    // 是否渲染的模板
    boolean isSlot() default false;
    // 是否这个参数也需要继续toRenderAttr
    boolean isJsonObject() default false;
    // 是否这个参数是map->eval类型
    boolean isMapEval() default false;
}
