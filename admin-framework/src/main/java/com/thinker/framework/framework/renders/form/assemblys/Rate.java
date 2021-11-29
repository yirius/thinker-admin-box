package com.thinker.framework.framework.renders.form.assemblys;

import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Rate extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Integer max;

    /**
     * 是否允许半选
     */
    @ComponentAttr(prevStr = ":")
    private Boolean allowHalf;

    /**
     * 低分和中等分数的界限值， 值本身被划分在低分中
     */
    @ComponentAttr(prevStr = ":")
    private Integer lowThreshold;

    /**
     * 高分和中等分数的界限值， 值本身被划分在高分中
     */
    @ComponentAttr(prevStr = ":")
    private Integer highThreshold;

    /**
     * icon 的颜色。 若传入数组，共有 3 个元素，为 3 个分段所对应的颜色；若传入对象，可自定义分段，键名为分段的界限值，键值为对应的颜色
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<String> colors = Arrays.asList("#A7A239", "#D3CD48", "#F7F055");

    /**
     * 	未选中 icon 的颜色
     */
    @ComponentAttr
    private String voidColor;

    /**
     * 只读时未选中 icon 的颜色
     */
    @ComponentAttr
    private String disabledVoidColor;

    /**
     * 只读时未选中 icon 的颜色
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<String> icons;

    /**
     * 	未被选中的图标组件
     */
    @ComponentAttr
    private String voidIcon;

    /**
     * 	禁用状态的未选择图标
     */
    @ComponentAttr
    private String disabledVoidIcon;

    /**
     * 	是否显示辅助文字，若为真，则会从 texts 数组中选取当前分数对应的文字内容
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showText;

    /**
     * 	是否显示当前分数， 是否显示当前分数， show-score 和 show-text 不能同时为真
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showScore;

    /**
     * 	辅助文字的颜色
     */
    @ComponentAttr
    private String textColor;

    /**
     * 	辅助文字数组
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<String> texts;

    /**
     * 分数显示模板
     */
    @ComponentAttr
    private String scoreTemplate;

    public Rate(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value,newValue")
    private String change;

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        return "<el-rate v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) +"></el-rate>";
    }
}
