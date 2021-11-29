package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Dict;
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
public class Slider extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Integer max;

    @ComponentAttr(prevStr = ":")
    private Integer min;

    @ComponentAttr(prevStr = ":")
    private Integer step;

    @ComponentAttr(prevStr = ":")
    private Boolean showInput;

    @ComponentAttr(prevStr = ":")
    private Boolean showInputControls;

    @ComponentAttr
    private LayoutLargeSize inputSize;

    @ComponentAttr(prevStr = ":")
    private Boolean showStops;

    @ComponentAttr(prevStr = ":")
    private Boolean showTooltip;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "value")
    private String formatTooltip;

    @ComponentAttr(prevStr = ":")
    private Boolean range;

    @ComponentAttr(prevStr = ":")
    private Boolean vertical;

    @ComponentAttr(prevStr = ":")
    private Integer height;

    @ComponentAttr(prevStr = ":")
    private Integer debounce;

    @ComponentAttr
    private String tooltipClass;

    @ComponentAttr(prevStr = ":", isReactive = true)
    private Dict marks;

    public Slider(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value,newValue")
    private String change;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value,newValue")
    private String input;

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        return "<el-slider v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) +"></el-slider>";
    }
}
