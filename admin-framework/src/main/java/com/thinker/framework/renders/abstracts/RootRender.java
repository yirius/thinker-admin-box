package com.thinker.framework.renders.abstracts;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.utils.ToolsUtil;
import com.thinker.framework.renders.DefineComponent;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Setter
@Getter
@Accessors(chain = true)
public abstract class RootRender {

    public RootRender() {
        setId(getClass().getSimpleName().toLowerCase());
    }

    @ToRenderAttrs
    public String id;

    // 保留参数
    public String component = "div";
    public Dict attrs = Dict.create();
    public List<RootRender> children = new ArrayList<>();
    public Map<String, List<RootRender>> slots = new HashMap<>();

    // 渲染所有组件到attrs里
    public void toRenderAttrs(Dict attrs, Object useClass) {
        String className = DefineComponent.parserAnnotationFields(useClass, ToRenderAttrs.class);
        if(DefineComponent.annotationFields.get(className).size() > 0) {
            DefineComponent.annotationFields.get(className).forEach((s, annotation) -> {
                Object fieldValue = BeanUtil.getFieldValue(useClass, s);
                if(fieldValue != null) {
                    ToRenderAttrs toRenderAttrs = (ToRenderAttrs) DefineComponent.annotationFields.get(className).get(s);
                    if(toRenderAttrs.isEval()) {
                        attrs.set(s, "[`eval`]" + fieldValue);
                    } else if(toRenderAttrs.isSlot()) {
                        if(fieldValue instanceof List || fieldValue instanceof RootRender) {
                            if(fieldValue instanceof List) {
                                List<RenderResult> renderResults = new ArrayList<>();
                                ((List<?>) fieldValue).forEach(root -> {
                                    renderResults.add(((RootRender) root).render());
                                });
                                attrs.set("#"+s.replace("Tpl", ""), renderResults);
                            } else {
                                attrs.set("#"+s.replace("Tpl", ""), ((RootRender) fieldValue).render());
                            }
                        } else {
                            attrs.set("#"+s.replace("Tpl", ""), "[`eval`]" + fieldValue);
                        }
                    } else if(toRenderAttrs.isModelRefsValue()) {
                        attrs.set(s, "[`eval`]props.modelRefsValue[\""+fieldValue+"\"]");
                    } else if(toRenderAttrs.toModelValue()) {
                        String modelValueField = BeanUtil.getFieldValue(useClass, "modelValue")+"_"+s;
                        attrs.set(s, "[`eval`]props.modelRefsValue['"+modelValueField+"']");
                        DefineComponent.setModelValue(modelValueField, fieldValue);
                    } else if(toRenderAttrs.isFormValue()) {
                        if(toRenderAttrs.isFormValueField()) {
                            // 如果是其他值关联了参数，需要直接写入
                            String modelValue = String.valueOf(BeanUtil.getFieldValue(useClass, "modelValue"));
                            attrs.set(s, "[`eval`]props.modelRefsValue[\""+modelValue+"\"]");
                            if(Validator.isEmpty(DefineComponent.getFormValue().get(modelValue))) {
                                DefineComponent.setFormValue(modelValue, fieldValue);
                            }
                        } else {
                            attrs.set(s, "[`eval`]props.modelRefsValue[\""+fieldValue+"\"]");

                            // 判断是不是需要设置一下modelValue，防止读不出来值
                            if(!DefineComponent.getFormValue().containsKey(String.valueOf(fieldValue)) ||
                                    DefineComponent.getFormValue().get(String.valueOf(fieldValue)) == null) {
                                // 判断是不是一个数组
                                Boolean isModelValueArray = (Boolean) BeanUtil.getFieldValue(useClass, "isModelValueArray");
                                // 判断是不是一个数字
                                Boolean isModelValueNumber = (Boolean) BeanUtil.getFieldValue(useClass, "isModelValueNumber");
                                if(isModelValueArray != null && isModelValueArray) {
                                    DefineComponent.setFormValue(String.valueOf(fieldValue), new JSONArray());
                                } else if(isModelValueNumber != null && isModelValueNumber) {
                                    DefineComponent.setFormValue(String.valueOf(fieldValue), BeanUtil.getFieldValue(useClass, "modelValueNumber"));
                                } else {
                                    DefineComponent.setFormValue(String.valueOf(fieldValue), "");
                                }
                            }
                        }
                    } else if(toRenderAttrs.isMapEval() && fieldValue instanceof Map) {
                        Dict mapEval = Dict.create();
                        ((Map<?, ?>) fieldValue).forEach((o, o2) -> {
                            mapEval.put(String.valueOf(o), "[`eval`]" + String.valueOf(o2));
                        });
                        attrs.set(s, mapEval);
                    } else {
                        if(toRenderAttrs.isJsonObject()) {
                            Dict fieldDict = Dict.create();
                            if(fieldValue instanceof Map) {
                                ((Map<?, ?>) fieldValue).forEach((o, o2) -> {
                                    if(o2 instanceof List) {
                                        List<Dict> valueListDict = new ArrayList<>();
                                        ((List) o2).forEach(obj -> {
                                            Dict valueDict = Dict.create();
                                            toRenderAttrs(valueDict, obj);
                                            valueListDict.add(valueDict);
                                        });
                                        fieldDict.set(String.valueOf(o), valueListDict);
                                    } else {
                                        Dict valueDict = Dict.create();
                                        toRenderAttrs(valueDict, o2);
                                        fieldDict.set(String.valueOf(o), valueDict);
                                    }
                                });
                                attrs.set(s, fieldDict);
                            } else if(fieldValue instanceof List) {
                                List<Dict> valueListDict = new ArrayList<>();
                                ((List) fieldValue).forEach(obj -> {
                                    Dict valueDict = Dict.create();
                                    toRenderAttrs(valueDict, obj);
                                    valueListDict.add(valueDict);
                                });
                                attrs.set(s, valueListDict);
                            }else {
                                toRenderAttrs(fieldDict, fieldValue);
                                attrs.set(s, fieldDict);
                            }
                        } else {
                            if(s.equals("importAttr")) {
                                attrs.set("import", fieldValue);
                            } else {
                                attrs.set(s, fieldValue);
                            }
                        }
                    }
                }
            });
            // 补充一下更新语句
            if (Validator.isNotEmpty(attrs.getStr("modelValue"))) {
                String modelValueName = String.valueOf(BeanUtil.getFieldValue(useClass, "modelValue"));
                if(Validator.isEmpty(attrs.getStr("onUpdate:modelValue"))) {
                    attrs.set(
                            "onUpdate:modelValue",
                            "[`eval`](value) => { props.modelRefsValue['"+modelValueName+"'].value = value; vm.emit('update:modelValue', value); }"
                    );
                }
            }
        }
    }

    /**
     * 便捷设置监听方法
     * @param event
     * @param execute
     * @return
     */
    public RootRender setOnEvent(String event, String execute) {
        getAttrs().set("on" + StrUtil.upperFirst(event), "[`eval`]" + execute);
        return this;
    }

    // 构建方法，渲染方法
    public abstract void beforeRender();
    public abstract RenderResult afterRender(RenderResult renderResult);

    public RenderResult render() {
        beforeRender();
        toRenderAttrs(attrs, this);

        Map<String, List<RenderResult>> slotRender = new HashMap<>();
        slots.forEach((s, rootRenders) -> slotRender.put(s, rootRenders.stream().map(RootRender::render).collect(Collectors.toList())));

        return afterRender(RenderResult.create(
                component, attrs,
                children.stream().map(rootRender -> {
                    rootRender.setId(this.getAttrs().getStr("id") + "_" + rootRender.getId());
                    return rootRender.render();
                }).collect(Collectors.toList()),
                slotRender
        ));
    }
}
