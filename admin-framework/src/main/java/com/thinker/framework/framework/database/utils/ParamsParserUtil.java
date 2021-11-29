package com.thinker.framework.framework.database.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.WrapperValue;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class ParamsParserUtil {

    /**
     * 获取参数列表
     * @return
     */
    private static Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        ThinkerAdmin.request().getRequest().getParameterMap().forEach((s, strings) -> params.put(s, StrUtil.join(",", (Object) strings)));
        return params;
    }

    public static void parseWrapper(QueryWrapper<?> queryWrapper, List<WrapperValue> wrapperValues) {
        parseWrapper(queryWrapper, wrapperValues, getParamsMap());
    }

    /**
     * 解析参数指定后的查询项目
     * @param queryWrapper
     * @param wrapperValues
     * @param params
     */
    public static void parseWrapper(QueryWrapper<?> queryWrapper, List<WrapperValue> wrapperValues, Map<String, Object> params) {
        //存在参数且不为空
        if(!Validator.isEmpty(wrapperValues) && wrapperValues.size() != 0) {
            //循环一遍查询参数
            wrapperValues.forEach(wrapperValue -> {
                //当前传递参数中存在指定alias
                Object paramObj = params.get(wrapperValue.getAlias());
                //判断参数是否为空
                if(Validator.isNotEmpty(paramObj)){
                    //找到并运行方法
                    try {
                        //判断是否存在函数计算
                        if(wrapperValue.getClosure() != null) {
                            wrapperValue.getClosure().run(queryWrapper, paramObj, params);
                        } else {
                            if(wrapperValue.getExpression().toLowerCase().contains("null")) {
                                Method method = queryWrapper.getClass().getMethod(wrapperValue.getExpression(), Object.class);
                                //运行相关方法
                                method.invoke(queryWrapper, getUseFieldName(wrapperValue));
                            } else {
                                Method method = queryWrapper.getClass().getMethod(wrapperValue.getExpression(), Object.class, Object.class);
                                //运行相关方法
                                method.invoke(queryWrapper, getUseFieldName(wrapperValue), paramObj);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * @title getWrapperFieldName
     * @description 找到设置的参数
     * @author YangYuanCe
     * @param wrapperValue
     * @return {@link String}
     **/
    private static String getUseFieldName(WrapperValue wrapperValue) {
        if(wrapperValue.getFieldName() != null && !wrapperValue.getFieldName().equals("")) {
            return wrapperValue.getFieldName();
        } else {
            return wrapperValue.getAlias();
        }
    }
}
