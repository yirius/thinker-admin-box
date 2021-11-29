package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Switchs extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Integer width;

    @ComponentAttr(prevStr = ":")
    private Boolean inlinePrompt;

    @ComponentAttr(prevStr = ":")
    private String activeIcon;

    @ComponentAttr(prevStr = ":")
    private String inactiveIcon;

    @ComponentAttr
    private String activeText;

    @ComponentAttr
    private String inactiveText;

    @ComponentAttr(prevStr = ":")
    private Integer activeValue = 1;

    @ComponentAttr(prevStr = ":")
    private Integer inactiveValue = 0;

    @ComponentAttr
    private String activeColor;

    @ComponentAttr
    private String inactiveColor;

    @ComponentAttr
    private String borderColor;

    @ComponentAttr(prevStr = ":", isEvent = true)
    private String beforeChange;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "val,newVal")
    private String change;

    public Switchs(String prop, String label) {
        super(prop, label);
    }

    @Override
    public String renderContent() {
        return "<el-switch v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) +" />";
    }
}
