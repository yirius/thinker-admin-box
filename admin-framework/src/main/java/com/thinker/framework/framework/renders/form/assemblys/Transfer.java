package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Transfer extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Boolean filterable;

    @ComponentAttr
    private String filterPlaceholder;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "value")
    private String filterMethod;

    public enum TargetOrder {
        ORIGINAL("original"), PUSH("push"), UNSHIFT("unshift");

        private final String size;
        TargetOrder(String size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return size;
        }
    }

    @ComponentAttr
    private TargetOrder targetOrder;

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<String> titles;

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<String> buttonTexts;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "h,option")
    private String renderContent;

    @ComponentAttr(prevStr = ":", isRef = true)
    private Dict format;

    @ComponentAttr(prevStr = ":", isRef = true)
    private Dict props = Dict.create().set("key", "value");

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<Object> leftDefaultChecked;

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<Object> rightDefaultChecked;

    public Transfer(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value,direction,keyArrays")
    private String change;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "keyArrays,newKeyArrays")
    private String leftCheckChange;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "keyArrays,newKeyArrays")
    private String rightCheckChange;

    private String defaultSlot;

    public String getDefaultSlotStr() {
        if(Validator.isNotEmpty(defaultSlot)) {
            return slotStr("default", "{option}", defaultSlot);
        }
        return "";
    }

    private String leftFooter;

    public String getLeftFooterSlot() {
        if(Validator.isNotEmpty(leftFooter)) {
            return slotStr("left-footer", "", leftFooter);
        }
        return "";
    }

    private String rightFooter;

    public String getRightFooterSlot() {
        if(Validator.isNotEmpty(rightFooter)) {
            return slotStr("right-footer", "", rightFooter);
        }
        return "";
    }

    protected List<LabelValue> options = new ArrayList<>();

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

        options.forEach(labelValue -> {
            if(!labelValue.containsKey("disabled")) labelValue.set("disabled", false);
        });
        PageParams.createRef(getLayoutId() + "_" + getProp() + "_data", JSON.toJSONString(options));

        return "<el-transfer :data=\""+getLayoutId() + "_" + getProp() + "_data\" v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+PageParams.strComponentAttrs(this)+">\n" +
                "" + getDefaultSlotStr() + getLeftFooterSlot() + getRightFooterSlot() +
                "</el-transfer>";
    }
}
