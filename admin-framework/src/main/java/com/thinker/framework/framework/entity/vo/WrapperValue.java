package com.thinker.framework.framework.entity.vo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @title WrapperValue
 * @description 便捷调用方法
 * @time 2020/2/7 4:32 下午
 * @return
 **/
@Getter
@Setter
@Accessors(chain = true)
public class WrapperValue implements Serializable {

    public WrapperValue(String alias, String expression){
        this.alias = alias;
        this.expression = expression;
    }

    public WrapperValue(String alias, String expression, String fieldName){
        this.alias = alias;
        this.expression = expression;
        this.fieldName = fieldName;
    }

    private String alias;
    private String expression;
    private String fieldName;
    private Object extField;
    private Closure closure;

    public interface Closure {
        void run(QueryWrapper<?> queryWrapper, Object param, Map<String, Object> paramMap);
    }
}
