package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.PopupPlacement;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class InputAutocomplete extends Input {

    @ComponentAttr
    private String valueKey;

    @ComponentAttr
    private String icon;

    /**
     * 获取输入建议的防抖延时ms, 300
     */
    @ComponentAttr
    private Integer debounce;

    @ComponentAttr
    private PopupPlacement placement;

    /**
     * 获取输入建议的方法， 仅当你的输入建议数据 resolve 时，通过调用 callback(data:[])  来返回它
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "queryString,callback")
    private String fetchSuggestions;

    /**
     * 自定义浮层类名
     */
    @ComponentAttr
    private String popperClass;

    /**
     * 是否在输入框 focus 时显示建议列表
     */
    @ComponentAttr(prevStr = ":")
    private Boolean triggerOnFocus;

    /**
     * 在输入没有任何匹配建议的情况下，按下回车是否触发 select 事件
     */
    @ComponentAttr(prevStr = ":")
    private Boolean selectWhenUnmatched;

    /**
     * 是否隐藏远程加载时的加载图标
     */
    @ComponentAttr(prevStr = ":")
    private Boolean hideLoading;

    /**
     * 是否将下拉列表插入至 body 元素。 是否将下拉列表插入至 body 元素。在下拉列表的定位出现问题时，可将该属性设置为 false
     */
    @ComponentAttr(prevStr = ":")
    private Boolean popperAppendToBody;

    /**
     * 是否默认突出显示远程搜索建议中的第一项
     */
    @ComponentAttr(prevStr = ":")
    private boolean highlightFirstItem = true;

    public InputAutocomplete(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true)
    private String select;

    /**
     * 默认模板的设置node,data
     */
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
        return "<el-autocomplete v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) +">" +
                getPrependSlot() + getPrefixSlot() + getSuffixSlot() + getAppendSlot() + getDefaultSlotStr() +
                "</el-autocomplete>";
    }
}
