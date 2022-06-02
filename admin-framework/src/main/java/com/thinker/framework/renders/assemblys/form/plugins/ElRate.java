package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElRate extends FormPluginRender<ElRate> {

    public ElRate() {
        setIsModelValueNumber(true);
    }

    @ToRenderAttrs
    private Integer max;

    @ToRenderAttrs
    private String size;

    @ToRenderAttrs
    private Boolean disabled;

    /**
     * 是否允许半选
     */
    @ToRenderAttrs
    private Boolean allowHalf;

    /**
     * 低分和中等分数的界限值， 值本身被划分在低分中
     */
    @ToRenderAttrs
    private Integer lowThreshold;

    /**
     * 高分和中等分数的界限值， 值本身被划分在高分中
     */
    @ToRenderAttrs
    private Integer highThreshold;

    /**
     * icon 的颜色。 若传入数组，共有 3 个元素，为 3 个分段所对应的颜色；若传入对象，可自定义分段，键名为分段的界限值，键值为对应的颜色
     */
    @ToRenderAttrs(toModelValue = true)
    private List<String> colors = Arrays.asList("#A7A239", "#D3CD48", "#F7F055");

    /**
     * 	未选中 icon 的颜色
     */
    @ToRenderAttrs
    private String voidColor;

    /**
     * 只读时未选中 icon 的颜色
     */
    @ToRenderAttrs
    private String disabledVoidColor;

    /**
     * 只读时未选中 icon 的颜色
     */
    @ToRenderAttrs(toModelValue = true)
    private List<String> icons;

    /**
     * 	未被选中的图标组件
     */
    @ToRenderAttrs
    private String voidIcon;

    /**
     * 	禁用状态的未选择图标
     */
    @ToRenderAttrs
    private String disabledVoidIcon;

    /**
     * 	是否显示辅助文字，若为真，则会从 texts 数组中选取当前分数对应的文字内容
     */
    @ToRenderAttrs
    private Boolean showText;

    /**
     * 	是否显示当前分数， 是否显示当前分数， show-score 和 show-text 不能同时为真
     */
    @ToRenderAttrs
    private Boolean showScore;

    /**
     * 	辅助文字的颜色
     */
    @ToRenderAttrs
    private String textColor;

    /**
     * 	辅助文字数组
     */
    @ToRenderAttrs(toModelValue = true)
    private List<String> texts;

    /**
     * 分数显示模板
     */
    @ToRenderAttrs
    private String scoreTemplate;

    @ToRenderAttrs
    private String onChange;

    @Override
    public void beforeRender() {
        setComponent("ElRate");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
