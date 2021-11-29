package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.LayoutLargeSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class InputNumber extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Integer max;

    @ComponentAttr(prevStr = ":")
    private Integer min;

    @ComponentAttr(prevStr = ":")
    private Integer step;

    @ComponentAttr(prevStr = ":")
    private Boolean stepStrictly;

    @ComponentAttr(prevStr = ":")
    private Integer precision;

    @ComponentAttr
    private LayoutLargeSize size;

    @ComponentAttr(prevStr = ":")
    private Boolean controls;

    @ComponentAttr
    private String controlsPosition;

    @ComponentAttr
    private String placeholder;

    public InputNumber(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String blur;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String focus;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        if(Validator.isEmpty(getDefaultValue())) {
            setDefaultValue(0);
        }

        return "<el-input-number v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) +"></el-input-number>";
    }
}
