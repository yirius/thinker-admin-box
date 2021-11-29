package com.thinker.framework.framework.renders;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.bo.PageImportCase;
import com.thinker.framework.framework.renders.tags.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class PageParams {

    /**
     * 引入文件, 根源方法
     * @param from
     */
    public static void setImport(String from, String ...moduleNames) {
        setImport(from, (new PageImportCase()).addModuleNames(Arrays.asList(moduleNames)));
    }

    public static void setImport(String from, String defaultName, boolean isStar, String ...moduleNames) {
        setImport(from, (new PageImportCase()).setDefaultName(defaultName).setStar(isStar).addModuleNames(Arrays.asList(moduleNames)));
    }

    public static void setImport(String from, PageImportCase pageImportCase) {
        Map<String, PageImportCase> concurrentMap = getImport();
        if(concurrentMap.containsKey(from)) {
            concurrentMap.get(from)
                    .setDefaultName(pageImportCase.getDefaultName())
                    .setStar(pageImportCase.isStar())
                    .addModuleNames(pageImportCase.getModuleNames());
        } else {
            concurrentMap.put(from, pageImportCase.setFrom(from));
        }
        ThinkerAdmin.thread().setObject("IMPORT_LIST", concurrentMap);
    }

    /**
     * 获取到导入方法
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, PageImportCase> getImport() {
        HashMap<String, PageImportCase> imports = (HashMap<String, PageImportCase>) ThinkerAdmin.thread().getObject("IMPORT_LIST");
        if(imports == null){
            imports = new HashMap<>();
        }
        return imports;
    }

    /**
     * 设置组件
     * @param componentNames
     */
    public static void setComponents(String ...componentNames) {
        List<String> stringList = getComponents();
        if(componentNames != null && componentNames.length > 0) {
            for (String componentName : componentNames) {
                if (!stringList.contains(componentName)) {
                    stringList.add(componentName);
                }
            }
        }
        ThinkerAdmin.thread().setObject("IMPORT_COMPONENTS", stringList);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getComponents() {
        List<String> components = (List<String>) ThinkerAdmin.thread().getObject("IMPORT_COMPONENTS");
        if(components == null){
            components = new ArrayList<>();
        }
        return components;
    }

    /**
     * 设置Setup里的执行语句
     */
    public static void setSetupScript(String scripts) {
        List<String> setupScript = getSetupScript();
        setupScript.add(scripts);
        ThinkerAdmin.thread().setObject("SETUP_SCRIPT", setupScript);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getSetupScript() {
        List<String> setupScript = (List<String>) ThinkerAdmin.thread().getObject("SETUP_SCRIPT");
        if(setupScript == null){
            setupScript = new ArrayList<>();
        }
        return setupScript;
    }

    /**
     * 设置Setup里在最为末尾的执行语句
     */
    public static void setSetupSuffixScript(String scripts) {
        List<String> setupScript = getSetupSuffixScript();
        setupScript.add(scripts);
        ThinkerAdmin.thread().setObject("SETUP_SUFFIX_SCRIPT", setupScript);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getSetupSuffixScript() {
        List<String> setupScript = (List<String>) ThinkerAdmin.thread().getObject("SETUP_SUFFIX_SCRIPT");
        if(setupScript == null){
            setupScript = new ArrayList<>();
        }
        return setupScript;
    }

    /**
     * 设置Setup里，获取数据完成后
     */
    public static void setParseSetupData(String scripts) {
        List<String> setupScript = getParseSetupData();
        setupScript.add(scripts);
        ThinkerAdmin.thread().setObject("SETUP_PARSE_DATA", setupScript);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getParseSetupData() {
        List<String> setupScript = (List<String>) ThinkerAdmin.thread().getObject("SETUP_PARSE_DATA");
        if(setupScript == null){
            setupScript = new ArrayList<>();
        }
        return setupScript;
    }

    /**
     * 创建ref、reactive格式参数，方便后续读取
     * @param fieldName
     * @param fieldValue
     */
    public static void createReactive(String fieldName, String fieldValue) {
        setSetupScript("const " + fieldName + " = reactive(" + fieldValue + ");");
        setSetupReturn(fieldName, fieldName);
    }

    public static void createRef(String fieldName, String fieldValue) {
        setSetupScript("const " + fieldName + " = ref(" + fieldValue + ");");
        setSetupReturn(fieldName, fieldName);
    }

    /**
     * 设置返回的参数
     * @param fieldName
     * @param fieldValue
     */
    public static void setSetupReturn(String fieldName, String fieldValue) {
        LinkedHashMap<String, String> setupReturn = getSetupReturn();
        setupReturn.put(fieldName, fieldValue);
        ThinkerAdmin.thread().setObject("SETUP_RETURN", setupReturn);
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> getSetupReturn() {
        LinkedHashMap<String, String> setupReturn = (LinkedHashMap<String, String>) ThinkerAdmin.thread().getObject("SETUP_RETURN");
        if(setupReturn == null){
            setupReturn = new LinkedHashMap<>();
        }
        return setupReturn;
    }

    /**
     * 设置method方法
     * @param method
     * @param data
     */
    public static void setMethods(String method, String data) {
        Dict optionsMethods = getMethods();
        optionsMethods.put(method, data);
        ThinkerAdmin.thread().setObject("OPTIONS_METHODS", optionsMethods);
    }

    public static Dict getMethods() {
        Dict optionsMethods = (Dict) ThinkerAdmin.thread().getObject("OPTIONS_METHODS");
        if(optionsMethods == null){
            optionsMethods = Dict.create();
        }
        return optionsMethods;
    }

    /**
     * 设置prop方法，可以直接设置方法/文字等
     * @param prop
     * @param data
     */
    public static void setProps(String prop, String data) {
        Dict optionsProps = getProps();
        optionsProps.put(prop, data);
        ThinkerAdmin.thread().setObject("OPTIONS_PROPS", optionsProps);
    }

    public static Dict getProps() {
        Dict optionsProps = (Dict) ThinkerAdmin.thread().getObject("OPTIONS_PROPS");
        if(optionsProps == null){
            optionsProps = Dict.create();
        }
        return optionsProps;
    }

    /**
     * 设置计算方法
     * @param prop
     * @param data
     */
    public static void setComputed(String prop, String data) {
        Dict optionsComputed = getComputed();
        optionsComputed.put(prop, data);
        ThinkerAdmin.thread().setObject("OPTIONS_COMPUTED", optionsComputed);
    }

    public static Dict getComputed() {
        Dict optionsComputed = (Dict) ThinkerAdmin.thread().getObject("OPTIONS_COMPUTED");
        if(optionsComputed == null){
            optionsComputed = Dict.create();
        }
        return optionsComputed;
    }

    /**
     * 设置观测方法
     * @param prop
     * @param data
     */
    public static void setWatch(String prop, String data) {
        Dict optionsWatch = getComputed();
        optionsWatch.put(prop, data);
        ThinkerAdmin.thread().setObject("OPTIONS_WATCH", optionsWatch);
    }

    public static Dict getWatch() {
        Dict optionsWatch = (Dict) ThinkerAdmin.thread().getObject("OPTIONS_WATCH");
        if(optionsWatch == null){
            optionsWatch = Dict.create();
        }
        return optionsWatch;
    }

    /**
     * 设置渲染的模板内容
     * @param layout
     * @param <LA>
     */
    public static <LA extends LayoutAbstract> void setTplLayout(LA layout) {
        List<LayoutAbstract> tplLayout = getTplLayout();
        tplLayout.add(layout);
        ThinkerAdmin.thread().setObject("TPL_LAYOUT", tplLayout);
    }

    @SuppressWarnings("unchecked")
    public static <LA extends LayoutAbstract> List<LA> getTplLayout() {
        List<LA> tplLayout = (List<LA>) ThinkerAdmin.thread().getObject("TPL_LAYOUT");
        if(tplLayout == null){
            tplLayout = new ArrayList<>();
        }
        return tplLayout;
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

    /**
     * 将字段参数转化成string
     * @param obj
     * @return
     */
    public static String strComponentAttrs(LayoutAbstract obj) {
        return strComponentAttrs(obj, null);
    }

    public static String strComponentAttrs(LayoutAbstract obj, List<String> notStrFields) {
        String annotationName = parserAnnotationFields(obj, ComponentAttr.class);
        StrBuilder strBuilder = new StrBuilder();
        annotationFields.get(annotationName).forEach((s, annotation) -> {
            if(notStrFields == null || !notStrFields.contains(s)) {
                Object fieldValue = BeanUtil.getFieldValue(obj, s);
                if(fieldValue != null) {
                    ComponentAttr componentAttr = (ComponentAttr) annotation;
                    String fieldName = obj.getLayoutId() + "_" + s;
                    if(obj.getClass().getSuperclass().getName().contains("AssemblyAbstract")) {
                        fieldName = obj.getLayoutId() + "_" + ((AssemblyAbstract) obj).getProp() + "_" + s;
                    }
                    strBuilder.append(componentAttr.prevStr()).append(s).append("=\"");
                    if(componentAttr.isRef()) {
                        createRef(fieldName, JSON.toJSONString(fieldValue));
                        strBuilder.append(fieldName);
                    } else if(componentAttr.isReactive()) {
                        createReactive(fieldName, JSON.toJSONString(fieldValue));
                        strBuilder.append(fieldName);
                    } else if(componentAttr.isEvent()) {
                        setMethods(fieldName, "("+componentAttr.eventArgs()+") {"+fieldValue+"}");
                        strBuilder.append(fieldName).append(componentAttr.eventStateArgs());
                    } else {
                        if(fieldValue.getClass().getSimpleName().equals("String")) {
                            strBuilder.append(fieldValue);
                        } else if(fieldValue.getClass().getSuperclass().getName().equals("java.lang.Enum")) {
                            strBuilder.append(fieldValue.toString());
                        } else {
                            strBuilder.append(JSON.toJSONString(fieldValue));
                        }
                    }
                    strBuilder.append("\" ");
                }
            }
        });

        return strBuilder.toStringAndReset();
    }

    /**
     * 设置和读取表单的values
     * @param formRef
     * @param propName
     * @param value
     */
    public static void setFormValues(String formRef, String propName, Object value) {
        Map<String, Dict> formValues = getFormValues();
        if(!formValues.containsKey(formRef)) formValues.put(formRef, Dict.create());
        formValues.get(formRef).set(propName, value);
        ThinkerAdmin.thread().setObject("THINKER_FORM_VALUES", formValues);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Dict> getFormValues() {
        Map<String, Dict> formValues = (Map<String, Dict>) ThinkerAdmin.thread().getObject("THINKER_FORM_VALUES");
        if(formValues == null){
            formValues = new HashMap<>();
        }
        return formValues;
    }
}
