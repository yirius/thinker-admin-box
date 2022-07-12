package com.thinker.framework.renders.assemblys.form.plugins;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.tree.ElTreeProps;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElTree extends FormPluginRender<ElTree> {

    @ToRenderAttrs
    private String nodeKey = "value";

    @ToRenderAttrs
    private Boolean lazy;

    @ToRenderAttrs(isEval = true)
    private String load;

    @ToRenderAttrs
    private Boolean showCheckbox;

    @ToRenderAttrs(toModelValue = true)
    private List<LabelValue> data;

    @ToRenderAttrs(isJsonObject = true)
    private ElTreeProps props = new ElTreeProps();

    @ToRenderAttrs(isEval = true)
    private String renderContent = "(h, data) => { return h('span', null, h('span', null, data.node.label)); }";

    @ToRenderAttrs(isEval = true)
    private String onCheck;

    @Override
    public void beforeRender() {
        setIsModelValueArray(true);

        setComponent("ElTree");

        if(Validator.isEmpty(onCheck)) {
            onCheck = "(changeData, checkedNodes) => { " +
                    "   props.modelRefsValue['"+getModelValue()+"'].value = getOpArgs().AdminTool.objects.deepClone(checkedNodes.checkedKeys);" +
                    "}";
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        if(renderResult.getAttrs().containsKey("modelValue")) {
            renderResult.getAttrs().put("defaultCheckedKeys", renderResult.getAttrs().get("modelValue"));

            renderResult.getAttrs().remove("modelValue");
            renderResult.getAttrs().remove("onUpdate:modelValue");
        }

        return renderResult;
    }
}
