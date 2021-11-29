package com.thinker.framework.framework.renders.form.assemblys;

import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@Getter
@Setter
@Accessors(chain = true)
public class TimeSelect extends AssemblyAbstract {

    @ComponentAttr
    private LayoutSize size;

    /**
     * 文本框可输入
     */
    @ComponentAttr(prevStr = ":")
    private Boolean editable;

    /**
     * 	是否显示清除按钮
     */
    @ComponentAttr(prevStr = ":")
    private Boolean clearable;

    @ComponentAttr
    private String placeholder;

    /**
     * 自定义前缀图标
     */
    @ComponentAttr
    private String prefixIcon;

    /**
     * 清楚日期图标
     */
    @ComponentAttr
    private String clearIcon;

    @ComponentAttr(prevStr = ":", isRef = true)
    private String start;

    @ComponentAttr(prevStr = ":", isRef = true)
    private String end;

    @ComponentAttr(prevStr = ":", isRef = true)
    private String step;

    @ComponentAttr(prevStr = ":", isRef = true)
    private String minTime;

    @ComponentAttr(prevStr = ":", isRef = true)
    private String maxTime;

    public TimeSelect(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "obj")
    private String blur;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "obj")
    private String focus;

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        return "<el-time-select v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this, Arrays.asList("prop", "label")) +" >" +
                "</el-time-select>";
    }
}
