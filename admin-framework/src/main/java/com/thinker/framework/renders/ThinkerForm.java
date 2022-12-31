package com.thinker.framework.renders;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.abstracts.form.FormRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.ElFormItem;
import com.thinker.framework.renders.assemblys.form.plugins.*;
import com.thinker.framework.renders.assemblys.page.ElTabPane;
import com.thinker.framework.renders.assemblys.page.ElTabs;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.ElSizeEnum;
import com.thinker.framework.renders.entity.table.EditRuleItem;
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
public class ThinkerForm extends FormRender {

    public ThinkerForm() {
        setId(DefineComponent.getRenderId() + "_" + getId());
    }

    /**
     * 便捷方法运行
     * @param runClosure
     * @return
     */
    public ThinkerForm runClosure(RunClosure<ThinkerForm> runClosure) {
        runClosure.run(this);
        return this;
    }

    // 标签的长度，例如 '50px'。 作为 Form 直接子元素的 form-item 会继承该值。 可以使用 auto。
    @ToRenderAttrs
    private String labelWidth = "100px";

    @ToRenderAttrs
    private String labelPosition;

    @ToRenderAttrs
    private String labelSuffix;

    @ToRenderAttrs
    private Boolean hideRequiredAsterisk;

    @ToRenderAttrs
    private Boolean showMessage;

    @ToRenderAttrs
    private Boolean inlineMessage;

    // 是否在输入框中显示校验结果反馈图标
    @ToRenderAttrs
    private Boolean statusIcon;

    // 是否在 rules 属性改变后立即触发一次验证
    @ToRenderAttrs
    private Boolean validateOnRuleChange;

    @ToRenderAttrs
    private ElSizeEnum size;

    // 是否禁用该表单内的所有组件。 如果设置为 true, 它将覆盖内部组件的 disabled 属性。
    @ToRenderAttrs
    private Boolean disabled;

    // 所有的规则
    @ToRenderAttrs(isJsonObject = true)
    private Map<String, List<EditRuleItem>> rules;

    /**
     * 检查是否存在tabs组件
     */
    private ElTabs elTabs;

    /**
     *
     * @param tabPaneRunClosure
     * @return
     */
    public ElTabPane tabPane(String label, String name, RunClosure<ElTabPane> tabPaneRunClosure) {
        if(elTabs == null) elTabs = new ElTabs();
        return elTabs.addTabPane(label, name, tabPaneRunClosure);
    }

    // 提交方法
    public ElFormItem submit(String url) {
        ElFormItem elFormItem = addFormItem("", "");
        elFormItem.getChildren().add(new ElButton().setLabel("提交").setOnClick(
                thinkerrender.adminbox.form.submitForm.template(url).render().toString()
        ));
        return elFormItem;
    }

    /**
     * 设置api参数
     */
    private String api;

    @ToRenderAttrs(isEval = true)
    private String onSubmit;

    public ThinkerResponse page() {
        return page(null);
    }

    public ThinkerResponse page(ThinkerPage.RunClosure runClosure) {
        return new ThinkerPage(thinkerPage -> {
            thinkerPage.getChildren().add(this);

            if(runClosure != null) {
                runClosure.run(thinkerPage);
            }
        }).render();
    }

    @Override
    public void beforeRender() {
        setComponent("ElForm");

        if(Validator.isNotEmpty(api)) {
            onSubmit = "($refs, triggerId) => { " +
                    "   var saveData = {};" +
                    "   for(var i in props.formValue) {" +
                    "       if(_AdminIs.isArray(props.formValue[i]) || _AdminIs.isObject(props.formValue[i])) {" +
                    "           saveData[i] = JSON.stringify(props.formValue[i]);" +
                    "           if(_AdminIs.isArray(props.formValue[i]) && saveData[i].indexOf('name') > 0 && (saveData[i].indexOf('url') > 0 || saveData[i].indexOf('response') > 0)) {" +
                    "               var arrSaveData = [];" +
                    "               props.formValue[i].forEach(item => {" +
                    "                   arrSaveData.push({name: item.name, url: item.response?item.response.data[i]:item.url});" +
                    "               });" +
                    "               saveData[i] = JSON.stringify(arrSaveData);" +
                    "           }" +
                    "       } else {" +
                    "           saveData[i] = props.formValue[i]" +
                    "       }" +
                    "   }" +
                    "   var extInfo = $refs[triggerId].layer.extInfo || {};" +
                    "   if(extInfo.id) saveData.id = extInfo.id;" +
                    "   _RequestApi.postRequest(\""+api+"\", saveData).then(response => {" +
                    "       $refs[triggerId].layer.show = false;" +
                    "       var tableName = triggerId.replace('_formlayer', '');" +
                    "       if($refs[tableName]) { $refs[tableName].vxeGrid.commitProxy('query'); }" +
                    "   })" +
                    " }";
        }

        if(elTabs != null) {
            getChildren().add(elTabs);
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        renderResult.getAttrs().set("model", "[`eval`]props.formValue");
        return renderResult;
    }
}
