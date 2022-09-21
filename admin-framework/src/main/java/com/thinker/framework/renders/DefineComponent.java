package com.thinker.framework.renders;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.utils.ToolsUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DefineComponent {

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

    /**
     * 设置modelValue
     * @param field
     * @param value
     */
    public static void setModelValue(String field, Object value) {
        ThinkerAdmin.thread().setObject("PAGE_MODEL_VALUE", getModelValue().set(field, value));
    }

    public static void setModelValue(Dict data) {
        Dict pageData = getModelValue();
        pageData.putAll(data);
        ThinkerAdmin.thread().setObject("PAGE_MODEL_VALUE", pageData);
    }

    public static Dict getModelValue() {
        Dict pageData = (Dict) ThinkerAdmin.thread().getObject("PAGE_MODEL_VALUE");
        if(pageData == null){
            pageData = Dict.create();
        }
        return pageData;
    }

    public static void setFormValue(String field, Object value) {
        ThinkerAdmin.thread().setObject("PAGE_FORM_VALUE", getFormValue().set(field, value));
    }

    public static void setFormValue(Dict data) {
        Dict pageData = getFormValue();
        pageData.putAll(data);
        ThinkerAdmin.thread().setObject("PAGE_FORM_VALUE", pageData);
    }

    public static Dict getFormValue() {
        Dict pageData = (Dict) ThinkerAdmin.thread().getObject("PAGE_FORM_VALUE");
        if(pageData == null){
            pageData = Dict.create();
        }
        return pageData;
    }

    /**
     * 添加运行完之后的执行策略
     * @param renderReadyStr
     */
    public static void addRenderReady(String renderReadyStr) {
        List<String> renderReady = getRenderReady();
        renderReady.add(renderReadyStr);
        ThinkerAdmin.thread().setObject("PAGE_RENDER_READY", renderReady);
    }

    public static List<String> getRenderReady() {
        List<String> renderReady = (List<String>) ThinkerAdmin.thread().getObject("PAGE_RENDER_READY");
        if(renderReady == null){
            renderReady = new ArrayList<>();
        }
        return renderReady;
    }

    /**
     * 添加获取数据或重置数据完成之后的方法
     * @param renderDataReadyStr
     */
    public static void addRenderDataReady(String renderDataReadyStr) {
        List<String> renderDataReady = getRenderDataReady();
        renderDataReady.add(renderDataReadyStr);
        ThinkerAdmin.thread().setObject("PAGE_RENDER_DATA_READY", renderDataReady);
    }

    public static List<String> getRenderDataReady() {
        List<String> renderDataReady = (List<String>) ThinkerAdmin.thread().getObject("PAGE_RENDER_DATA_READY");
        if(renderDataReady == null){
            renderDataReady = new ArrayList<>();
        }
        return renderDataReady;
    }

    /**
     * 添加获取数据或重置数据完成之后的方法
     * @param getDataBeforeStr
     */
    public static void addGetDataBefore(String getDataBeforeStr) {
        List<String> getDataBefore = getGetDataBefore();
        getDataBefore.add(getDataBeforeStr);
        ThinkerAdmin.thread().setObject("PAGE_GET_DATA_BEFORE", getDataBefore);
    }

    public static List<String> getGetDataBefore() {
        List<String> getDataBefore = (List<String>) ThinkerAdmin.thread().getObject("PAGE_GET_DATA_BEFORE");
        if(getDataBefore == null){
            getDataBefore = new ArrayList<>();
        }
        return getDataBefore;
    }

    /**
     * 添加一个新的组件
     * @param path
     * @param name
     */
    public static void addImportComponent(String path, String name) {
        List<Dict> importComponent = getImportComponent();
        importComponent.add(Dict.create().set("path", path).set("name", name));
        ThinkerAdmin.thread().setObject("PAGE_RENDER_COMPONENT", importComponent);
    }

    public static List<Dict> getImportComponent() {
        List<Dict> importComponent = (List<Dict>) ThinkerAdmin.thread().getObject("PAGE_RENDER_COMPONENT");
        if(importComponent == null){
            importComponent = new ArrayList<>();
        }
        return importComponent;
    }

    /**
     * 计算一下渲染id
     * @return
     */
    public static String getRenderId() {
        String parentCallName = ThinkerAdmin.thread().getString("CURRENT_CLASS_METHOD", null);
        if(parentCallName == null) {
            int count = 0;
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            //循环找到名称
            for (StackTraceElement stackTraceElement: stackTraceElements) {
                if(stackTraceElement.getClassName().contains("sun.reflect.") || stackTraceElement.getClassName().contains("BySpringCGLIB")){//分割分类名
                    break;
                }
                //最多找10次，就不找了
                count++;
                if(count > 9){
                    break;
                }
            }
            --count;
            //如果存在该参数
            if(count <= 9 && stackTraceElements.length >= count) {
                String className = stackTraceElements[count].getClassName().toLowerCase();
                String methodName = stackTraceElements[count].getMethodName().toLowerCase();

                List<String> classNames = StrUtil.split(className, ".");
                parentCallName = (classNames.get(classNames.size()-1) + "_" + methodName).replace("controller_", "_");
            }

            //清空队列
            stackTraceElements = null;

            if(parentCallName == null){
                parentCallName = ToolsUtil.rand(12, ToolsUtil.RandFormat.CHAR).toLowerCase();
            }

            ThinkerAdmin.thread().setObject("CURRENT_CLASS_METHOD", parentCallName.replace("$", "").replace("`", ""));
        }

        return parentCallName;
    }
}
