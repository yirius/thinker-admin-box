package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SelectVirtualized extends Select {

    @ComponentAttr(prevStr = ":")
    private Boolean automaticDropdown;

    @ComponentAttr(prevStr = ":")
    private Integer height;

    @ComponentAttr(prevStr = ":")
    private Boolean scrollbarAlwaysOn;

    public SelectVirtualized(String prop, String label) {
        super(prop, label);
    }

    private String defaultSlot;

    public String getDefaultSlotStr() {
        if(Validator.isNotEmpty(defaultSlot)) {
            return slotStr("default", "{item}", defaultSlot);
        }
        return "";
    }

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        options.forEach(labelValue -> {
            if(!labelValue.containsKey("disabled")) labelValue.set("disabled", false);
        });
        PageParams.createRef(getLayoutId() + "_" + getProp() + "_options", JSON.toJSONString(options));

        return "<el-select-v2 :options=\""+getLayoutId() + "_" + getProp() + "_options\" v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) + "> " +
                "" + getDefaultSlotStr() + getEmptySlot() +
                "</el-select-v2>";
    }
}
