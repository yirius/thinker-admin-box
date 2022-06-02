package com.thinker.framework.renders.abstracts.form;

import com.thinker.framework.renders.DefineComponent;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.ElFormItem;
import com.thinker.framework.renders.assemblys.page.ElCol;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public abstract class FormPluginRender<T extends RootRender> extends RootRender {
    public T runClosure(RunClosure<T> runClosure) {
        runClosure.run((T) this);
        return (T) this;
    }

    @ToRenderAttrs
    private String name;

    // 设置是否可以折叠
    private Boolean folding;

    @ToRenderAttrs(isFormValue = true)
    private String modelValue;

    // modelValue是不是一个数组
    private Boolean isModelValueArray = false;

    // modelValue是不是一个数字
    private Boolean isModelValueNumber = false;

    // modelValue是不是一个数字
    private Integer modelValueNumber = 0;

    // 记录上一层的参数
    private ElFormItem elFormItem;

    // 设置elCol分段
    @Setter(AccessLevel.NONE)
    private ElCol elCol;

    public ElCol openRowCol() {
        if(elCol == null) {
            elCol = new ElCol();
        }
        if(elFormItem != null) elFormItem.setElCol(elCol);
        return elCol;
    }

    /**
     * 调整formItem参数
     * @param elFormItemRunClosure
     * @return
     */
    public T adjustFormItem(RunClosure<ElFormItem> elFormItemRunClosure) {
        if(elFormItem != null) {
            elFormItemRunClosure.run(elFormItem);
        }
        return (T) this;
    }

    public T setDefaultValue(Object value) {
        DefineComponent.setFormValue(getModelValue(), value);
        return (T) this;
    }
}
