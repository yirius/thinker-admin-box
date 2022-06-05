package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.select.ElOption;
import com.thinker.framework.renders.assemblys.form.plugins.select.ElOptionGroup;
import com.thinker.framework.renders.entity.RenderResult;
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
public class ElSelect extends FormPluginRender<ElSelect> {

    // 默认显示内容
    @ToRenderAttrs
    private String placeholder;

    // 是否可以多选
    @ToRenderAttrs
    private Boolean multiple;

    // 多选限制条数
    @ToRenderAttrs
    private Integer multipleLimit;

    // 是否禁用
    @ToRenderAttrs(isEval = true)
    private Boolean disabled;

    // 作为 value 唯一标识的键名，绑定值为对象类型时必填
    @ToRenderAttrs
    private String valueKey;

    // 输入框尺寸
    @ToRenderAttrs
    private String size;

    // 是否可以清空选项
    @ToRenderAttrs
    private Boolean clearable;

    // 多选时是否将选中值按文字的形式展示
    @ToRenderAttrs
    private Boolean collapseTags;

    // 当鼠标悬停于折叠标签的文本时，是否显示所有选中的标签。 要使用此属性，collapse-tags属性必须设定为 true
    @ToRenderAttrs
    private Boolean collapseTagsTooltip;

    // Tooltip 主题，内置了 dark / light 两种
    @ToRenderAttrs
    private String effect;

    // Select 输入框的原生 autocomplete 属性
    @ToRenderAttrs
    private String autocomplete;

    // Select 组件是否可筛选
    @ToRenderAttrs
    private Boolean filterable;

    public ElSelect search() { setFilterable(true); return this; }

    // 是否允许用户创建新条目， 只有当 filterable 设置为 true 时才会生效。
    @ToRenderAttrs
    private Boolean allowCreate;

    // 自定义筛选方法
    @ToRenderAttrs(isEval = true)
    private String filterMethod;

    // 远程搜索
    @ToRenderAttrs
    private Boolean remote;

    // 自定义远程搜索方法
    @ToRenderAttrs(isEval = true)
    private String remoteMethod;

    // Select 组件是否从远程加载数据
    @ToRenderAttrs(isEval = true)
    private Boolean loading;

    // 从服务器加载内容时显示的文本
    @ToRenderAttrs
    private String loadingText;

    // 搜索条件无匹配时显示的文字，也可以使用 empty 插槽设置
    @ToRenderAttrs
    private String noMatchText;

    // 无选项时显示的文字
    @ToRenderAttrs
    private String noDataText;

    // 当 multiple 和 filter被设置为 true 时，是否在选中一个选项后保留当前的搜索关键词
    @ToRenderAttrs
    private Boolean reserveKeyword;

    // 是否在输入框按下回车时，选择第一个匹配项。 需配合 filterable 或 remote 使用
    @ToRenderAttrs
    private Boolean defaultFirstOption;

    // 对于不可搜索的 Select，是否在输入框获得焦点后自动弹出选项菜单
    @ToRenderAttrs
    private Boolean automaticDropdown;

    // 下拉框的宽度是否与输入框相同
    @ToRenderAttrs
    private Boolean fitInputWidth;

    // 标签类型
    @ToRenderAttrs
    private String tagType;

    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onVisibleChange;

    @ToRenderAttrs(isEval = true)
    private String onRemoveTag;

    @ToRenderAttrs(isEval = true)
    private String onClear;

    @ToRenderAttrs(isEval = true)
    private String onBlur;

    @ToRenderAttrs(isEval = true)
    private String onFocus;

    private List<LabelValue> options;

    // 添加组别
    public ElSelect addGroupOptions(String label, List<LabelValue> options) {
        this.options = options;
        ElOptionGroup elOptionGroup = new ElOptionGroup();
        elOptionGroup.setOptions(options).getAttrs().set("label", label);
        getChildren().add(elOptionGroup);
        return this;
    }

    public ElSelect addOptions(List<LabelValue> options) {
        this.options = options;
        getChildren().addAll(options.stream().map(labelValue -> (new ElOption()).setAttrs(labelValue)).collect(Collectors.toList()));
        return this;
    }

    @Override
    public void beforeRender() {
        setComponent("ElSelect");

        if(options != null) {
            addOptions(options);
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
