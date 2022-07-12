package com.thinker.framework.framework.database.relation.parser;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.framework.framework.database.exceptions.LazyWithFillException;
import com.thinker.framework.framework.database.relation.HasManyEntity;
import com.thinker.framework.framework.database.relation.HasOneEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@Accessors(chain = true)
public class EntityConfig {
    // 类型，1是HasOne，2是HasMany
    private int type = 1;
    // with的时候需要写的名称
    private String fieldName;
    // 实体类
    private Class<?> entity;
    // 参数
    List<String> condition;
    Map<String, String> conditionStr = new HashMap<>();
    // 是否直接连接
    boolean isJoin = false;
    // 连接方式
    String joinType = "LEFT";
    // 读取字段
    String field = "*";
    // 字段分析
    String fieldStr;
    // 字段实体
    Field fieldIns;
    // 表名
    String tableName;

    public EntityConfig(Field field, Annotation annotation) {
        this.fieldName = field.getName();
        this.fieldIns = field;
        if(annotation instanceof HasOneEntity) {
            this.setEntity(((HasOneEntity) annotation).entity())
                    .setCondition(Arrays.asList(((HasOneEntity) annotation).condition()))
                    .setJoin(((HasOneEntity) annotation).isJoin())
                    .setJoinType(((HasOneEntity) annotation).joinType())
                    .setField(((HasOneEntity) annotation).field());
        } else if(annotation instanceof HasManyEntity) {
            this.setType(2).setEntity(((HasManyEntity) annotation).entity())
                    .setCondition(Arrays.asList(((HasManyEntity) annotation).condition()))
                    .setField(((HasManyEntity) annotation).field());
        }
    }

    /**
     * 获取到当前的表名
     * @return
     */
    public String getTableName() {
        if(Validator.isEmpty(this.tableName)) {
            TableName tableName = this.entity.getAnnotation(TableName.class);
            this.tableName = tableName == null ? StrUtil.toUnderlineCase(this.entity.getSimpleName()) : tableName.value();
        }
        return this.tableName;
    }

    /**
     * 拼接join参数
     * @param joinName
     * @return
     */
    public String getConditionStr(String joinName) {
        if(!this.conditionStr.containsKey(joinName)) {
            this.conditionStr.put(joinName,
                    StrUtil.join(" ", condition)
                    .replace("this.", joinName + ".")
                    .replace("that.", this.fieldName + ".")
                    .replace(" eq ", " = ")
                    .replace(" ne ", " <> ")
                    .replace(" lt ", " < ")
                    .replace(" gt ", " > ")
            );
        }
        return this.conditionStr.get(joinName);
    }

    /**
     * 获取到延迟参数
     * @param originData
     * @return
     */
    public String getLazyConditionStr(Map<String, Object> originData) throws LazyWithFillException {
        StrBuilder strBuilder = new StrBuilder();
        for (int i = 0; i < condition.size(); i++) {
            if(condition.get(i).contains("this.")) {
                String oriField = condition.get(i).replace("this.", "");
                if(originData.containsKey(oriField)) {
                    strBuilder.append(originData.get(oriField));
                } else {
                    throw new LazyWithFillException(
                            "message.thinker.exceptions.fillNoField",
                            "数据中不存在参数"+oriField+",无法进行填充", 440, Dict.create().set("field", oriField)
                    );
                }
            } else {
                strBuilder.append(
                        condition.get(i)
                        .replace("that.", "")
                        .replace("eq", " = ")
                        .replace("ne", " <> ")
                        .replace("lt", " < ")
                        .replace("gt", " > ")
                );
            }
        }
        return strBuilder.toStringAndReset();
    }

    /**
     * 读取的字段，连表也算
     * @return
     */
    public String getFieldStr() {
        if(Validator.isEmpty(this.fieldStr)) {
            List<String> fieldList = ParserHasEntity.getEntityFieldMap(this.entity);

            StrBuilder strBuilder = new StrBuilder();
            String[] splited = this.field.split(",");
            for (int i = 0; i < splited.length; i++) {
                if(splited[i].equals("*")) {
                    for (int i1 = 0; i1 < fieldList.size(); i1++) {
                        strBuilder.append(",")
                                .append(this.fieldName).append(".")
                                .append(fieldList.get(i1))
                                .append(" as ")
                                .append(this.fieldName).append("_____").append(fieldList.get(i1));
                    }
                } else {
                    strBuilder.append(",")
                            .append(!splited[i].contains(".") ? this.fieldName + "." : "")
                            .append(splited[i])
                            .append(" as ")
                            .append(this.fieldName).append("_____").append(splited[i]);
                }
            }
            this.fieldStr = strBuilder.toStringAndReset();
        }

        return this.fieldStr;
    }
}
