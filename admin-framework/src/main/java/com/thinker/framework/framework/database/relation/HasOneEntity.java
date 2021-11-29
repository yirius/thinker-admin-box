package com.thinker.framework.framework.database.relation;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HasOneEntity {

    /**
     * @title entity
     * @description 对应的entity类
     * @author YangYuanCe
     * @return {@link Class}
     **/
    Class<?> entity();

    /**
     * @title condition
     * @description JOIN连接条件
     * @author YangYuanCe
     * @return {@link String}
     **/
    String[] condition();

    /**
     * @title isJoin
     * @description 是否开启join
     * @author YangYuanCe
     * @return {@link boolean}
     **/
    boolean isJoin() default false;

    /**
     * @title joinType
     * @description 默认连接方式
     * @author YangYuanCe
     * @return {@link String}
     **/
    String joinType() default "LEFT";

    /**
     * 读取的字段
     * @return
     */
    String field() default "*";
}
