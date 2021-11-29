package com.thinker.framework.framework.database.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.database.exceptions.LazyWithFillException;
import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import com.thinker.framework.framework.database.relation.parser.EntityConfig;
import com.thinker.framework.framework.database.relation.parser.ParserHasEntity;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DatabaseUtil {

    /**
     * 获取到表名称
     * @param entity
     * @return
     */
    public static String getTableName(Class<?> entity) {
        TableName tableName = entity.getAnnotation(TableName.class);
        return tableName == null ? StrUtil.toUnderlineCase(entity.getSimpleName()) : tableName.value();
    }

    /**
     * 在查询之前，检查一下Wrapper的字段是否齐全
     * @param entity
     * @param thinkerWrapper
     * @param <T>
     */
    public static <T> void checkWrapperFields(Class<?> entity, ThinkerWrapper<T> thinkerWrapper, ThinkerMapper<T> thinkerMapper) {
        if(Validator.isEmpty(thinkerWrapper.getSqlTableName())) {
            if(thinkerMapper == null) {
                thinkerWrapper.setSqlTableName(ShardingUtil.checkShardingTableAndGetName(entity));
            } else {
                thinkerWrapper.setSqlTableName(ShardingUtil.checkShardingTableAndGetName(thinkerMapper, getTableName(entity)));
            }
        }

        //判断是否存在字段，不存在就塞进去
        if(Validator.isEmpty(thinkerWrapper.getSqlSelect())){
            thinkerWrapper.select("*");
        }

        // 判断with参数
        if(thinkerWrapper.getWiths().size() > 0) {
            // 有需要连表的语句, 再去校验with参数
            if(ParserHasEntity.haveJoinEntity(entity)) {
                ParserHasEntity.wrapperJoin(entity, thinkerWrapper);
            }
        }
    }

    public static <T> void checkWrapperFields(Class<?> entity, ThinkerWrapper<T> thinkerWrapper) {
        checkWrapperFields(entity, thinkerWrapper, null);
    }

    /**
     * 存在连表的join查询时候，直接渲染查询结果
     * @param thinkerMapper
     * @param thinkerWrapper
     * @param <T>
     */
    public static <T> List<Map<String, Object>> injectJoinFillResult(
            Class<?> entity, ThinkerMapper<T> thinkerMapper, ThinkerWrapper<T> thinkerWrapper
    ) {
        List<Map<String, Object>> mapList = thinkerMapper.thinkersql(thinkerWrapper);
        // 判断是不是有JOIN，没有JOiN就不执行
        if(ParserHasEntity.haveJoinEntity(entity) && thinkerWrapper.getWiths().size() > 0) {
            mapList = mapList.stream().peek(DatabaseUtil::parserWithForMap).collect(Collectors.toList());
        }

        try{
            // 如果提交参数里有with字段，需要填充一下
            if(thinkerWrapper.getWiths().size() > 0) {
                String[] withs = new String[thinkerWrapper.getWiths().size()];
                for (int i = 0; i < thinkerWrapper.getWiths().size(); i++) {
                    withs[i] = thinkerWrapper.getWiths().get(i);
                }
                // 开始填充
                for (int i = 0; i < mapList.size(); i++) {
                    injectWithResult(thinkerMapper, entity, mapList.get(i), false, withs);
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return mapList;
    }

    /**
     * 渲染_____五个下划线的join到map里
     * @param stringObjectMap
     */
    public static void parserWithForMap(Map<String, Object> stringObjectMap) {
        Map<String, Dict> withMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> iterable = stringObjectMap.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<String, Object> s = iterable.next();
            if(s.getKey().contains("_____")) {
                String[] splited = s.getKey().split("_____");
                if(!withMap.containsKey(splited[0])) withMap.put(splited[0], Dict.create());
                withMap.get(splited[0]).put(splited[1], s.getValue());
                iterable.remove();
            }
        }
        if(withMap.size() > 0) {
            stringObjectMap.putAll(withMap);
        }
    }

    /**
     * 填充最终结果，当存在with字段的时候
     * @param thinkerMapper
     * @param entityClass
     * @param entityObj
     * @param withs
     * @param <T>
     * @param <M>
     * @throws LazyWithFillException
     */
    @SuppressWarnings("unchecked")
    public static <T, M extends ThinkerMapper<T>> void injectWithResult(
            M thinkerMapper, Class<?> entityClass, Object entityObj, boolean injectJoin, String ...withs
    ) throws LazyWithFillException {
        List<EntityConfig> entityConfigList = ParserHasEntity.parserEntity(entityClass);
        List<String> withList = Arrays.asList(withs);
        ThinkerMapper.ThinkerQueryParam thinkerQueryParam = new ThinkerMapper.ThinkerQueryParam();
        Map<String, Object> map = BeanUtil.beanToMap(entityObj, true, false);
        // 循环一遍，查找信息
        for (int i = 0; i < entityConfigList.size(); i++) {
            if(!(!injectJoin && entityConfigList.get(i).isJoin())) {
                if(withList.size() == 0 || withList.contains(entityConfigList.get(i).getFieldName())) {
                    // 先组装一下查询的sql
                    String whereSql = entityConfigList.get(i).getLazyConditionStr(map);
                    String md5 = SecureUtil.md5(
                            entityConfigList.get(i).getField() + entityConfigList.get(i).getTableName() + whereSql
                    );

                    // 在当前线程里，只判断一次即可
                    Object result = ThinkerAdmin.thread().getObject("WITH_" + md5);
                    if(result == null) {
                        // 全都渲染，或渲染指定的
                        thinkerQueryParam.setSql(
                                "select " + entityConfigList.get(i).getField() + " from " + entityConfigList.get(i).getTableName() +
                                        " where " + whereSql
                        );
                        result = thinkerMapper.thinkerquery(thinkerQueryParam);
                        ThinkerAdmin.thread().setObject("WITH_" + md5, result);
                    }

                    if(entityObj.getClass().getSimpleName().equals("Map")) {
                        if(entityConfigList.get(i).getType() == 1) {
                            ((Map) entityObj).put(entityConfigList.get(i).getFieldName(), ((List) result).size()==0 ? null : ((List) result).get(0));
                        } else if(entityConfigList.get(i).getType() == 2) {
                            ((Map) entityObj).put(entityConfigList.get(i).getFieldName(), result);
                        }
                    } else {
                        if(entityConfigList.get(i).getType() == 1) {
                            List<?> list = JSON.parseArray(JSON.toJSONString(result), entityConfigList.get(i).getFieldIns().getType());
                            BeanUtil.setFieldValue(entityObj, entityConfigList.get(i).getFieldName(), list.size()==0 ? null : list.get(0));
                        } else if(entityConfigList.get(i).getType() == 2) {
                            Class<?> fieldType = (Class<?>) ((ParameterizedType) entityConfigList.get(i).getFieldIns().getGenericType()).getActualTypeArguments()[0];
                            BeanUtil.setFieldValue(entityObj, entityConfigList.get(i).getFieldName(), JSON.parseArray(JSON.toJSONString(result), fieldType));
                        }
                    }
                }
            }
        }
    }
}
