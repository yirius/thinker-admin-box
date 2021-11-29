package com.thinker.framework.framework.renders.form;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thinker.framework.framework.abstracts.renders.AssemblyLayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.renders.enums.FormItemSize;
import com.thinker.framework.framework.renders.page.ThinkerPage;
import com.thinker.framework.framework.renders.bo.FormRulesCase;
import com.thinker.framework.framework.renders.form.assemblys.Button;
import com.thinker.framework.framework.renders.page.plugins.ThinkerTab;
import com.thinker.framework.framework.renders.page.plugins.ThinkerTabPane;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ThinkerForm extends AssemblyLayoutAbstract {

    @ComponentAttr(prevStr = ":")
    private Boolean inline;

    @ComponentAttr
    private String labelPosition;

    @ComponentAttr
    private String labelWidth = "120px";

    @ComponentAttr
    private String labelSuffix;

    @ComponentAttr(prevStr = ":")
    private Boolean hideRequiredAsterisk;

    @ComponentAttr(prevStr = ":")
    private Boolean showMessage;

    @ComponentAttr(prevStr = ":")
    private Boolean inlineMessage;

    @ComponentAttr(prevStr = ":")
    private Boolean statusIcon;

    @ComponentAttr(prevStr = ":")
    private Boolean validateOnRuleChange;

    @ComponentAttr
    private FormItemSize size = FormItemSize.NORMAL;

    @ComponentAttr(prevStr = ":")
    private Boolean disabled;

    public ThinkerForm() {
        setLayoutId(getLayoutId() + "_form");
    }

    public ThinkerForm(Closure closure) {
        setLayoutId(getLayoutId() + "_form");
        if(closure != null) closure.run(this);
    }

    //使用闭包的形式
    public interface Closure {
        void run(ThinkerForm thinkerForm);
    }

    /**
     * 规则内容
     */
    @Setter(AccessLevel.NONE)
    private Map<String, List<FormRulesCase>> rules = new HashMap<>();

    // 便捷添加规则
    public ThinkerForm addRule(String fieldName, FormRulesCase.Closure closure) {
        if(!rules.containsKey(fieldName)) rules.put(fieldName, new ArrayList<>());
        rules.get(fieldName).add(new FormRulesCase(closure));

        return this;
    }

    // 规则输出字符串
    public String getRulesStr() {
        if(rules != null && rules.size() > 0) {
            StrBuilder fieldsStr = new StrBuilder().append("{\n");
            rules.forEach((s, formRules) -> fieldsStr.append(s).append(":").append("[").append(StrUtil.join(",", formRules)).append("],\n"));
            PageParams.createRef(getLayoutId() + "_rules", fieldsStr.toStringAndReset() + "}");

            return ":rules=\""+getLayoutId()+"_rules\"";
        }
        return "";
    }

    /**
     * tab的内容，不能直接设置
     */
    @Setter(AccessLevel.NONE)
    private ThinkerTab tab = null;

    /**
     * 生成一个tab的内容
     * @param label
     * @param name
     * @param closure
     * @return
     */
    public ThinkerTab tabPane(String label, String name, ThinkerTabPane.Closure closure) {
        if(tab == null) tab = initLayoutAbstract(new ThinkerTab());
        tab.tabPane(label, name, closure);
        return tab;
    }

    /**
     * 是否有弹出层, 不允许设置
     */
    @Setter(AccessLevel.NONE)
    private ThinkerFormPopup formPopup = new ThinkerFormPopup();

    /**
     * 清除弹出层，说明是平面展示
     * @return
     */
    public ThinkerForm clearPopup() {
        if(formPopup != null) formPopup = null;
        return this;
    }

    /**
     * 提交按钮的设置
     */
    @Setter(AccessLevel.NONE)
    private Button submitBtn = new Button("submit", "提交").setType(ButtonType.PRIMARY);

    public String getSubmitBtnStr() {
        if(formPopup == null && submitBtn != null) {
            return (new ThinkerFormItem()).setContent(submitBtn.render().toString()).render().toString();
        }

        return "";
    }

    /**
     * 提交网址
     */
    private String submitUrl;
    /**
     * 获取数据内容的网址
     */
    private String readUrl;
    /**
     * 处理提交的数据，进行其他操作
     */
    private String parseSubmitData = "";
    /**
     * 数据展示成功后，进行其他操作
     */
    private String parseSetupData = "";

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        // 获取到formValue的值
        String formValueName = getLayoutId() + "_formValue";

        String content = "<el-form "+getRulesStr()+" "+PageParams.strComponentAttrs(this)+" ref=\""+getLayoutId()+"\" :model=\""+formValueName+"\">\n" +
                (tab != null ? tab.render() : "") +
                // 渲染一下是否存在分行
                renderRow(
                        // 所有的组件 + 提交按钮
                        "   " + getAssemblysRender() + "   " + getSubmitBtnStr()
                ) +
                "</el-form>\n";

        // 需要等渲染完了，才能设置script
        if(Validator.isNotEmpty(submitUrl)) {
            PageParams.setSetupScript(thinkerrender.adminbox.form.setupRenderValues.template(
                    formValueName, Validator.isNotEmpty(readUrl) ? readUrl : submitUrl, parseSetupData + StrUtil.join("\n", PageParams.getParseSetupData())
            ).render().toString());

            PageParams.setImport("@/api/request", "getRequest", "postRequest", "putRequest");
            PageParams.setMethods(getLayoutId()+"_submitData", thinkerrender.adminbox.form.submitEvent.template(getLayoutId(), submitUrl, parseSubmitData).render().toString());
            PageParams.setMethods(getLayoutId()+"_validateData", thinkerrender.adminbox.form.submitValidate.template(getLayoutId(), "this."+getLayoutId()+"_submitData();").render().toString());
        }

        // 设置formValue值
        PageParams.createRef(
                getLayoutId() + "_formValue",
                JSON.toJSONString(PageParams.getFormValues().get(getLayoutId()), SerializerFeature.WriteMapNullValue)
        );

        if(formPopup != null) {
            return initLayoutAbstract(formPopup).setContent(content).render();
        }

        return content;
    }

    /**
     * 返回界面内容，可以进行二次渲染
     * @param closure
     * @return
     */
    public ThinkerPage page(ThinkerPage.Closure closure) {
        return new ThinkerPage(thinkerPage -> {
            thinkerPage.setLayoutContainer(false);
            thinkerPage.getLayouts().add(this);
            if(closure != null) {
                closure.run(thinkerPage);
            }
        });
    }

    public ThinkerPage page() {
        return this.page(null);
    }
}
