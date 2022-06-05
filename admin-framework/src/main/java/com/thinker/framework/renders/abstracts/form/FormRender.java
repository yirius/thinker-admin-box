package com.thinker.framework.renders.abstracts.form;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.assemblys.form.ElFormItem;
import com.thinker.framework.renders.assemblys.form.plugins.*;

public abstract class FormRender extends RootRender {
    // 整体参数设置
    public ElFormItem addFormItem(String field, String label) {
        ElFormItem elFormItem = new ElFormItem().setLabel(label).setProp(field);
        getChildren().add(elFormItem);
        return elFormItem;
    }

    // 添加input组件
    public ElInput input(String field, String label) {
        return addFormItem(field, label).input(field);
    }

    // 添加input组件
    public ElInputRange inputRange(String field, String label) {
        return addFormItem(field, label).inputRange(field);
    }

    // 添加inputNumber组件
    public ElInputNumber inputNumber(String field, String label) {
        return addFormItem(field, label).inputNumber(field);
    }

    // 添加select组件
    public ElSelect select(String field, String label) {
        return addFormItem(field, label).select(field);
    }

    // 添加cascader组件
    public ElCascader cascader(String field, String label) {
        return addFormItem(field, label).cascader(field);
    }

    // 添加checkbox组件
    public ElCheckbox checkbox(String field, String label) {
        return addFormItem(field, label).checkbox(field);
    }

    // 添加checkbox组件
    public ElCheckboxGroup checkboxGroup(String field, String label) {
        return addFormItem(field, label).checkboxGroup(field);
    }

    // 添加checkbox组件
    public ElRadio radio(String field, String label) {
        return addFormItem(field, label).radio(field);
    }

    // 添加checkbox组件
    public ElRadioGroup radioGroup(String field, String label) {
        return addFormItem(field, label).radioGroup(field);
    }

    // 添加checkbox组件
    public ElColorPicker colorPicker(String field, String label) {
        return addFormItem(field, label).colorPicker(field);
    }

    // 添加checkbox组件
    public ElDatePicker datePicker(String field, String label) {
        return addFormItem(field, label).datePicker(field);
    }

    public ElRate rate(String field, String label) {
        return addFormItem(field, label).rate(field);
    }

    public ElSlider slider(String field, String label) {
        return addFormItem(field, label).slider(field);
    }

    public ElSwitch switchs(String field, String label) {
        return addFormItem(field, label).switchs(field);
    }

    public ElTimePicker timePicker(String field, String label) {
        return addFormItem(field, label).timePicker(field);
    }

    public ElTimeSelect timeSelect(String field, String label) {
        return addFormItem(field, label).timeSelect(field);
    }

    public ElTransfer transfer(String field, String label) {
        return addFormItem(field, label).transfer(field);
    }

    public ElUpload upload(String field, String label) {
        return addFormItem(field, label).upload(field);
    }

    public ElTree tree(String field, String label) {
        return addFormItem(field, label).tree(field);
    }
}
