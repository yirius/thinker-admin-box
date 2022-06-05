package com.thinker.framework.renders.assemblys.form;

import com.alibaba.fastjson.JSON;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerTags;
import com.thinker.framework.renders.assemblys.form.plugins.*;
import com.thinker.framework.renders.assemblys.page.ElCol;
import com.thinker.framework.renders.assemblys.page.ElImageViewer;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.ElSizeEnum;
import com.thinker.framework.renders.entity.table.EditRuleItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElFormItem extends RootRender {

    @ToRenderAttrs
    private String label;

    @ToRenderAttrs
    private String prop;

    @ToRenderAttrs
    private String labelWidth;

    @ToRenderAttrs
    private Boolean required;

    @ToRenderAttrs
    private String error;

    @ToRenderAttrs
    private Boolean showMessage;

    @ToRenderAttrs
    private Boolean inlineMessage;

    @ToRenderAttrs
    private ElSizeEnum size;

    @ToRenderAttrs(isJsonObject = true)
    private List<EditRuleItem> rules;

    public <T extends FormPluginRender<T>> T addFormPlugin(T formPlugin, String field) {
        getChildren().add(formPlugin.setElFormItem(this).setName(field).setModelValue(field).setId(field));
        return formPlugin;
    }

    public ElInput input(String field) {
        return addFormPlugin(new ElInput(), field);
    }

    public ElInputRange inputRange(String field) {
        return addFormPlugin(new ElInputRange(), field);
    }

    public ElInputNumber inputNumber(String field) {
        return addFormPlugin(new ElInputNumber(), field);
    }

    public ElSelect select(String field) {
        return addFormPlugin(new ElSelect(), field);
    }

    public ElCascader cascader(String field) {
        return addFormPlugin(new ElCascader(), field);
    }

    public ElCheckbox checkbox(String field) {
        return addFormPlugin(new ElCheckbox(), field);
    }

    public ElCheckboxGroup checkboxGroup(String field) {
        return addFormPlugin(new ElCheckboxGroup(), field);
    }

    public ElRadio radio(String field) {
        return addFormPlugin(new ElRadio(), field);
    }

    public ElRadioGroup radioGroup(String field) {
        return addFormPlugin(new ElRadioGroup(), field);
    }

    public ElColorPicker colorPicker(String field) {
        return addFormPlugin(new ElColorPicker(), field);
    }

    public ElDatePicker datePicker(String field) {
        return addFormPlugin(new ElDatePicker(), field);
    }

    public ElRate rate(String field) {
        return addFormPlugin(new ElRate(), field);
    }

    public ElSlider slider(String field) {
        return addFormPlugin(new ElSlider(), field);
    }

    public ElSwitch switchs(String field) {
        return addFormPlugin(new ElSwitch(), field);
    }

    public ElTimePicker timePicker(String field) {
        return addFormPlugin(new ElTimePicker(), field);
    }

    public ElTimeSelect timeSelect(String field) {
        return addFormPlugin(new ElTimeSelect(), field);
    }

    public ElTransfer transfer(String field) {
        return addFormPlugin(new ElTransfer(), field);
    }

    public ElTree tree(String field) {
        return addFormPlugin(new ElTree(), field);
    }

    public ElUpload upload(String field) {
        getChildren().add(new ThinkerTags().runClosure(rootRender -> {
            rootRender.getChildren().add(new ElImageViewer().setModelValue(field));
        }));
        return addFormPlugin(new ElUpload(), field);
    }

    // 设置elCol分段
    private ElCol elCol;

    @Override
    public void beforeRender() {
        setComponent("ElFormItem");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        if(elCol == null) {
            return renderResult;
        } else {
            RenderResult elColRenderResult = elCol.render();
            elColRenderResult.getChildren().add(renderResult);
            return elColRenderResult;
        }
    }
}
