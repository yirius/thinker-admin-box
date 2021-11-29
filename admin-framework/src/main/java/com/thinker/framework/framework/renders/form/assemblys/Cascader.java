package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.CascaderProps;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Cascader extends AssemblyAbstract {

    /**
     * 可选项数据源，键名可通过 Props 属性配置
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<LabelValue> options = new ArrayList<>();

    @ComponentAttr
    private LayoutSize size;

    @ComponentAttr
    private String placeholder;

    @ComponentAttr(prevStr = ":")
    private Boolean clearable;

    /**
     * 	输入框中是否显示选中值的完整路径
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showAllLevels;

    /**
     * 多选模式下是否折叠Tag
     */
    @ComponentAttr(prevStr = ":")
    private Boolean collapseTags;

    /**
     * 选项分隔符, 默认为/
     */
    @ComponentAttr
    private String separator;

    /**
     * 是否可搜索选项
     */
    @ComponentAttr(prevStr = ":")
    private Boolean filterable;

    /**
     * 搜索回调，参数为"node,keyword"
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "node,keyword")
    private Boolean filterMethod;

    /**
     * 搜索关键词输入的去抖延迟，毫秒, 默认300
     */
    @ComponentAttr(prevStr = ":")
    private Integer debounce;

    /**
     * 过滤函数调用之前的钩子函数，该函数接受一个 value 参数。 如果该函数的返回值是 false 或者是一个被拒绝的Promise，那么接下来的过滤便不会执行。
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "value")
    private String beforeFilter;

    /**
     * 自定义浮层类名
     */
    @ComponentAttr
    private String popperClass;

    /**
     * 是否将弹出框插入至 body 元素。 在弹出框的定位出现问题时，可将该属性设置为 false
     */
    @ComponentAttr(prevStr = ":")
    private Boolean popperAppendToBody;

    public Cascader(String prop, String label) {
        super(prop, label);
    }

    /**
     * 当绑定值变化时触发的事件
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    /**
     * 当绑定值变化时触发的事件
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "data")
    private String expandChange;

    /**
     * 	当失去焦点时触发
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String blur;

    /**
     * 	当失去焦点时触发
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String focus;

    /**
     * 	下拉框出现/隐藏时触发
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "isShow")
    private String visibleChange;

    /**
     * 	在多选模式下，移除Tag时触发
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "tag")
    private String removeTag;

    /**
     * 属性设置
     */
    private CascaderProps props;

    public String getPropsStr() {
        if(props != null) {
            if(props.isLazy() && Validator.isNotEmpty(props.getLazyLoad())) {
                PageParams.setSetupScript("const "+getLayoutId()+"_"+getProp()+"_lazyLoad = (node,resolve) => {" +
                        "" + props.getLazyLoad() +
                        "}");

                props.setLazyLoad("[|js|]"+getLayoutId()+"_"+getProp()+"_lazyLoad[|js|]");
            } else {
                props.setLazyLoad(null);
            }

            PageParams.createRef(getLayoutId()+"_"+getProp()+"_props", JSON.toJSONString(props)
                    .replace("\"[|js|]", "").replace("[|js|]\"", "")
            );

            return ":props=\""+getLayoutId()+"_"+getProp()+"_props\"";
        }

        return "";
    }

    /**
     * 默认模板的设置node,data
     */
    private String defaultSlot;

    public String getDefaultSlotStr() {
        if(Validator.isNotEmpty(defaultSlot)) {
            return slotStr("default", "{node,data}", defaultSlot);
        }
        return "";
    }

    /**
     * 无匹配选项时的内容
     */
    private String emptySlot;

    public String getEmptySlotStr() {
        if(Validator.isNotEmpty(emptySlot)) {
            return slotStr("empty", null, emptySlot);
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
        if(Validator.isEmpty(getDefaultValue())) {
            setDefaultValue(new JSONArray());
        }

        return "<el-cascader v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this, Arrays.asList("prop", "label")) +" "+getPropsStr()+">" +
                getDefaultSlotStr() +
                getEmptySlotStr() +
                "</el-cascader>";
    }
}
