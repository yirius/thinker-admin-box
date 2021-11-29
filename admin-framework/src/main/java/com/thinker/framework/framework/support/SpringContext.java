package com.thinker.framework.framework.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 框架的IOC类，便捷获取内部参数
 */
@Slf4j
public class SpringContext {
    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    // 通过Type获取到指定参数的实例
    private static final Map<String, Object> typeBean = new HashMap<>();
    public static <T> T getOneBeanUseType(Class<T> clazz) {
        if(!typeBean.containsKey(clazz.getName())) {
            Map<String, T> map = getApplicationContext().getBeansOfType(clazz);
            if(map.keySet().size() == 0) {
                return null;
            }
            typeBean.put(clazz.getName(), map.get(String.valueOf(map.keySet().toArray()[0])));
        }
        return (T) typeBean.get(clazz.getName());
    }
}
