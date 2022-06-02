package com.thinker.framework.renders.utils;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RenderJsonUtil {

    public static String parserType(String str) {
        return "`|tf|`"+str+"`|tf|`";
    }

    /**
     * 找到各个数组的参数
     */
    public static Map<String, Map<String, Annotation>> annotationFields = new HashMap<>();

    public static <A extends Annotation> String parserAnnotationFields(Object obj, Class<A> annotation) {
        String fullName = obj.getClass().getName() + ":" + annotation.getSimpleName();
        if(!annotationFields.containsKey(fullName)) {
            annotationFields.put(fullName, new HashMap<>());
            Field[] aList = ReflectUtil.getFields(obj.getClass());
            for (Field field : aList) {
                Annotation fieldAnnotation = field.getAnnotation(annotation);
                if (fieldAnnotation != null) {
                    annotationFields.get(fullName).put(field.getName(), fieldAnnotation);
                }
            }
        }
        return fullName;
    }
//    public static String toJSONString(Map<String, ?> obj) {
//        return toJSONString(obj, null);
//    }
//
//    public static String toJSONString(Map<String, ?> obj, List<String> notStrFields) {
//        StrBuilder strBuilder = new StrBuilder();
//        log.info(JSON.toJSONString(obj, new ContextValueFilter() {
//            @Override
//            public Object process(BeanContext beanContext, Object o, String s, Object o1) {
//                String annotationName = parserAnnotationFields(o1, FunctionInJson.class);
//                return JSON.toJSONString(o1, new ContextValueFilter() {
//                    @Override
//                    public Object process(BeanContext beanContext, Object o, String s, Object o1) {
//                        if(annotationFields.get(annotationName).containsKey(s)) {
//                            return parserType(JSON.toJSONString(o1));
//                        }
//                        return JSON.toJSONString(o1);
//                    }
//                });
//            }
//        }).replace("\\", "").replace("\"`|tf|`\"", "").replace("\"`|tf|`", "").replace("`|tf|`\"", ""));
//
//        return strBuilder.toStringAndReset();
//    }
}
