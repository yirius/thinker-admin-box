package com.thinker.framework.framework.abstracts.renders;

import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.form.ThinkerFormItem;
import com.thinker.framework.framework.renders.page.plugins.ThinkerRowCol;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 组件的基类
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class AssemblyAbstract extends LayoutAbstract {

    public AssemblyAbstract(String prop, String label) {
        setProp(prop);
        setLabel(label);
    }

    /**
     * 字段名, 通常是提交的formValue中的key
     */
    @ComponentAttr
    private String prop;

    /**
     * 标签名, 界面上展示的名称
     */
    @ComponentAttr
    private String label;

    /**
     * 是否执行验证事件，如果设置为false，则form提交时不进行该参数校验
     */
    @ComponentAttr(prevStr = ":")
    private Boolean validateEvent;

    /**
     * 是否禁止使用，如果直接禁止且不可更改，请设置为"true"(注意引号)。
     * 否则请使用createDisabled()
     */
    @ComponentAttr(prevStr = ":")
    private String disabled;

    /**
     * 创建disabled参数的setup语句，可操作更改
     */
    public AssemblyAbstract createDisabled() {
        return createDisabled(false);
    }

    public AssemblyAbstract createDisabled(boolean isDisabled) {
        PageParams.createRef(getLayoutId() + "_" + getProp() + "_disabled", JSON.toJSONString(isDisabled));
        this.disabled = getLayoutId() + "_" + getProp() + "_disabled";
        return this;
    }

    /**
     * 是否只读，如果直接只读且不可更改，请设置为"true"(注意引号)。
     * 否则请使用createDisabled()
     */
    @ComponentAttr(prevStr = ":")
    private String readonly;

    /**
     * 创建readonly参数的setup语句，可操作更改
     */
    public AssemblyAbstract createReadonly() {
        return createDisabled(false);
    }

    public AssemblyAbstract createReadonly(boolean isReadonly) {
        PageParams.createRef(getLayoutId() + "_" + getProp() + "_readonly", JSON.toJSONString(isReadonly));
        this.readonly = getLayoutId() + "_" + getProp() + "_readonly";
        return this;
    }

    /**
     * 是否创建一个加载中状态, 是一个ref参数，可操作更改
     */
    @ComponentAttr(prevStr = "v-", isRef = true)
    private Boolean loading;

    /**
     * 默认显示的参数值，如果是读取的时候为空显示(需要在_afterRead中设置)
     */
    private Object defaultValue = null;

    /**
     * 默认需要使用formitem包裹, 如果不使用，需要使用clear
     */
    @Setter(AccessLevel.NONE)
    private ThinkerFormItem formItem = new ThinkerFormItem();

    /**
     * 设置formitem，并运行closure
     * @param closure
     * @return
     */
    public AssemblyAbstract formItem(ThinkerFormItem.Closure closure) {
        if(formItem == null) {
            formItem = initLayoutAbstract(new ThinkerFormItem());
        }
        if(closure != null) closure.run(formItem);
        return this;
    }

    /**
     * 清空formitem，返回默认组件内容
     * @return
     */
    public AssemblyAbstract clearFormItem() {
        this.formItem = null;
        return this;
    }

    /**
     * 每一行的参数设置，默认为不打开，如果调用了openRowCol()，需要在parent中调用openRow()
     */
    @Setter(AccessLevel.NONE)
    private ThinkerRowCol rowCol = null;

    public ThinkerRowCol openRowCol() {
        if(rowCol == null) {
            rowCol = initLayoutAbstract(new ThinkerRowCol());
        }
        return rowCol;
    }

    /**
     * 由于组件都同质化，重写一下渲染函数
     * @return
     */
    public abstract String renderContent();

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        String content = renderContent();

        // 渲染完了，内容固定了，再去加入formValues参数
        PageParams.setFormValues(getLayoutId(), getProp(), getDefaultValue());

        if(formItem != null) {
            content = formItem.setProp(getProp()).setLabel(getLabel()).setContent(content).render().toString();
        }

        if(rowCol != null) {
            content = rowCol.setContent(content).render().toString();
        }

        return content;
    }
}
