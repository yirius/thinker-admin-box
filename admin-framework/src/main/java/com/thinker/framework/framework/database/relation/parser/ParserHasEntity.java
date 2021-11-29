package com.thinker.framework.framework.database.relation.parser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.framework.framework.database.entity.ThinkerEntity;
import com.thinker.framework.framework.database.exceptions.LazyWithFillException;
import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import com.thinker.framework.framework.database.relation.HasManyEntity;
import com.thinker.framework.framework.database.relation.HasOneEntity;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import com.thinker.framework.framework.support.SpringContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class ParserHasEntity {
    private static final Map<String, List<EntityConfig>> entityConfigMap = new HashMap<>();
    private static final Map<String, List<String>> entityFieldMap = new HashMap<>();

    public static List<EntityConfig> parserEntity(Class<?> entity) {
        if(entity == null) return null;

        if(!entityConfigMap.containsKey(entity.getName())) {
            for(Field field : ReflectUtil.getFieldsDirectly(entity, true)) {
                for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                    if(declaredAnnotation instanceof HasOneEntity || declaredAnnotation instanceof HasManyEntity) {
                        if(!entityConfigMap.containsKey(entity.getName())) entityConfigMap.put(entity.getName(), new ArrayList<>());

                        entityConfigMap.get(entity.getName()).add(
                                (new EntityConfig(field, declaredAnnotation))
                        );
                    }
                }
                // 判断一下是否不是字段
                TableField tableField = field.getAnnotation(TableField.class);
                if(tableField == null || tableField.exist()) {
                    if(!entityFieldMap.containsKey(entity.getName())) entityFieldMap.put(entity.getName(), new ArrayList<>());
                    if(tableField != null) {
                        entityFieldMap.get(entity.getName()).add(Validator.isNotEmpty(tableField.value()) ? tableField.value() : StrUtil.toUnderlineCase(field.getName()));
                    } else {
                        entityFieldMap.get(entity.getName()).add(StrUtil.toUnderlineCase(field.getName()));
                    }
                }
            }
        }

        return entityConfigMap.get(entity.getName());
    }

    public static List<String> getEntityFieldMap(Class<?> entity) {
        if(!entityFieldMap.containsKey(entity.getName())) {
            parserEntity(entity);
        }
        return entityFieldMap.get(entity.getName());
    }

    /**
     * 判断是否有强制连表操作，有的话返回true
     * @param entity
     * @return
     */
    public static boolean haveJoinEntity(Class<?> entity) {
        if(entity != null) {
            List<EntityConfig> entityConfigList = parserEntity(entity);
            if(entityConfigList != null && entityConfigList.size() > 0) {
                for (int i = 0; i < entityConfigList.size(); i++) {
                    if(entityConfigList.get(i).getType()==1&&entityConfigList.get(i).isJoin()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 将指定的Wrapper进行连表
     * @param entity
     * @param thinkerWrapper
     */
    public static void wrapperJoin(Class<?> entity, ThinkerWrapper<?> thinkerWrapper) {
        if(thinkerWrapper.getWiths().size() > 0) {
            List<EntityConfig> entityConfigList = parserEntity(entity);
            boolean isJoin = false;
            StrBuilder strBuilder = new StrBuilder();
            for (int i = 0; i < entityConfigList.size(); i++) {
                if(entityConfigList.get(i).getType()==1&&
                        entityConfigList.get(i).isJoin()&&
                        thinkerWrapper.getWiths().contains(entityConfigList.get(i).getFieldName())
                ) {
                    isJoin = true;
                    // 如果当前表还没有别名，给他设置一个
                    if(Validator.isEmpty(thinkerWrapper.getSqlTableAlias())) {
                        thinkerWrapper.setSqlTableAlias(entity.getSimpleName().toLowerCase());
                    }
                    // 组装一下join
                    thinkerWrapper.join(
                            entityConfigList.get(i).getTableName() + " as " + entityConfigList.get(i).getFieldName(),
                            entityConfigList.get(i).getConditionStr(thinkerWrapper.getSqlTableAlias()),
                            entityConfigList.get(i).getJoinType()
                    );
                    // 组装field
                    strBuilder.append(entityConfigList.get(i).getFieldStr());
                }
            }

            // 如果已经设置了其他的，需要对应一下参数
            if(isJoin) {
                // 组装当前主表的参数
                String selectStr = thinkerWrapper.getSqlSelect();
                // 多次调用Wrapper时，防止重复添加
                if(!selectStr.contains(strBuilder.toString())) {
                    StrBuilder newSelect = new StrBuilder();
                    if(selectStr.contains(",")) {
                        String[] splited = selectStr.split(",");
                        for (int i = 0; i < splited.length; i++) {
                            newSelect.append(",").append(!splited[i].contains(".") ? thinkerWrapper.getSqlTableAlias() + "." : "").append(splited[i]);
                        }
                    } else {
                        newSelect.append(",").append(thinkerWrapper.getSqlTableAlias()).append(".").append(selectStr);
                    }
                    // 主表和JOIN表拼接
                    thinkerWrapper.select(newSelect.toStringAndReset().substring(1) + strBuilder.toStringAndReset());
                }
            }
        }
    }
}
