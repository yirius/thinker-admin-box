package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.LayoutLargeSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Select extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Boolean multiple;

    @ComponentAttr
    private String valueKey;

    @ComponentAttr
    private LayoutLargeSize size;

    @ComponentAttr(prevStr = ":")
    private Boolean clearable;

    @ComponentAttr(prevStr = ":")
    private Boolean collapseTags;

    @ComponentAttr(prevStr = ":")
    private Integer multipleLimit;

    @ComponentAttr
    private String autocomplete;

    @ComponentAttr
    private String placeholder;

    @ComponentAttr(prevStr = ":")
    private Boolean filterable;

    @ComponentAttr(prevStr = ":")
    private Boolean allowCreate;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "value")
    private String filterMethod;

    @ComponentAttr(prevStr = ":")
    private Boolean remote;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "value")
    private String remoteMethod;

    @ComponentAttr(prevStr = ":", isRef = true)
    private Boolean loading;

    @ComponentAttr
    private String loadingText;

    @ComponentAttr
    private String noMatchText;

    @ComponentAttr
    private String noDataText;

    @ComponentAttr
    private String popperClass;

    @ComponentAttr(prevStr = ":")
    private Boolean reserveKeyword;

    @ComponentAttr(prevStr = ":")
    private Boolean defaultFirstOption;

    @ComponentAttr(prevStr = ":")
    private Boolean popperAppendToBody;

    @ComponentAttr(prevStr = ":")
    private Boolean automaticDropdown;

    @ComponentAttr(prevStr = ":")
    private Boolean fitInputWidth;

    public Select(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String blur;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String focus;

    @ComponentAttr(prevStr = "@", isEvent = true)
    private String clear;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "isShow")
    private String visibleChange;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String removeTag;

    private String prefix;

    public String getPrefixSlot() {
        if(Validator.isNotEmpty(prefix)) {
            return slotStr("prefix", "", prefix);
        }
        return "";
    }

    private String empty;

    public String getEmptySlot() {
        if(Validator.isNotEmpty(empty)) {
            return slotStr("empty", "", empty);
        }
        return "";
    }

    protected List<LabelValue> options = new ArrayList<>();
    private String optionsTemplate = "";

    @Override
    public String renderContent() {
        options.forEach(labelValue -> {
            if(!labelValue.containsKey("disabled")) labelValue.set("disabled", false);
        });
        PageParams.createRef(getLayoutId() + "_" + getProp() + "_options", JSON.toJSONString(options));

        return "<el-select v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+PageParams.strComponentAttrs(this)+" >\n" +
                "<el-option v-for=\"(item, index) in "+getLayoutId() + "_" + getProp() + "_options\" :key=\"index\" " +
                "   :label=\"item.label\" :value=\"item.value\" :disabled=\"item.disabled\" >\n" +
                optionsTemplate +
                "</el-option>\n" +
                "" + getPrefixSlot() + getEmptySlot() +
                "</el-select>";
    }
}
