package com.thinker.framework.framework.database.relation;

import java.lang.annotation.*;

/**
 * @title HasManyEntity
 * @description 便捷调用方法
 * @time 2020/2/7 3:12 下午
 * @return
 **/

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HasManyEntity {

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
     * @return {@link String[]}
     **/
    String[] condition();

    /**
     * 读取的字段
     * @return
     */
    String field() default "*";
}
