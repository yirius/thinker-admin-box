package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.select.ElOption;
import com.thinker.framework.renders.assemblys.form.plugins.select.ElOptionGroup;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.form.ElCascaderProps;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElCascader extends FormPluginRender<ElCascader> {

    public ElCascader() {
        setIsModelValueArray(true);
    }

    @ToRenderAttrs(toModelValue = true)
    List<LabelValue> options;

    // 配置选项
    @ToRenderAttrs(toModelValue = true)
    private ElCascaderProps props;

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 默认显示内容
    @ToRenderAttrs
    private String placeholder;

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 是否可以清空选项
    @ToRenderAttrs
    private Boolean clearable;

    // 输入框中是否显示选中值的完整路径
    @ToRenderAttrs
    private Boolean showAllLevels;

    // 多选时是否将选中值按文字的形式展示
    @ToRenderAttrs
    private Boolean collapseTags;

    // 当鼠标悬停于折叠标签的文本时，是否显示所有选中的标签。 要使用此属性，collapse-tags属性必须设定为 true
    @ToRenderAttrs
    private Boolean collapseTagsTooltip;

    // 用于分隔选项的字符
    @ToRenderAttrs
    private String separator;

    // Select 组件是否可筛选
    @ToRenderAttrs
    private Boolean filterable;

    public ElCascader search() { setFilterable(true); return this; }

    // 自定义筛选方法
    @ToRenderAttrs(isEval = true)
    private String filterMethod;

    // 过滤函数调用前欲被调用的钩子函数，该函数接受一个参数。 如果该函数的返回值是 false 或者是一个被拒绝的 Promise，那么接下来的过滤逻辑便不会执行。
    @ToRenderAttrs(isEval = true)
    private String beforeFilter;

    // 搜索关键词正在输入时的去抖延迟，单位为毫秒
    @ToRenderAttrs
    private Integer debounce;

    // 标签类型
    @ToRenderAttrs
    private String tagType;

    /** 开启事件 **/

    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onVisibleChange;

    @ToRenderAttrs(isEval = true)
    private String onExpandChange;

    @ToRenderAttrs(isEval = true)
    private String onRemoveTag;

    @ToRenderAttrs(isEval = true)
    private String onBlur;

    @ToRenderAttrs(isEval = true)
    private String onFocus;

    private Boolean isPanel = false;

    @Override
    public void beforeRender() {
        setComponent("ElCascader" + (isPanel ? "Panel" : ""));
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
