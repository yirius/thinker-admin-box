package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.CheckboxItems;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Checkbox extends AssemblyAbstract {

    @ComponentAttr
    private LayoutSize size = LayoutSize.NORMAL;

    /**
     * 可被勾选的 checkbox 的最小数量
     */
    @ComponentAttr(prevStr = ":")
    private Integer min;

    /**
     * 可被勾选的 checkbox 的最大数量
     */
    @ComponentAttr(prevStr = ":")
    private Integer max;

    /**
     * 按钮形式的 Checkbox 激活时的文本颜色
     */
    @ComponentAttr
    private String textColor;

    /**
     * 按钮形式的 Checkbox 激活时的填充色和边框色
     */
    @ComponentAttr
    private String fill;

    public Checkbox(String prop, String label) {
        super(prop, label);
    }

    /**
     * 触发事件
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    /**
     * 是否是按钮类型
     */
    private boolean isButton = false;

    // 内部的轮询值
    private List<CheckboxItems> options = new ArrayList<>();

    // 需要循环显示的值，可自行修改
    private String labelField = "{{item.label}}";

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
        PageParams.createReactive(getLayoutId()+"_"+getProp()+"_options", JSON.toJSONString(options, SerializerFeature.WriteEnumUsingToString));

        return "<el-checkbox-group v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+PageParams.strComponentAttrs(this)+">\n" +
                "<el-checkbox"+(isButton?"-button":"")+" v-for=\"(item, index) in "+getLayoutId()+"_"+getProp()+"_options\" :key=\"index\" " +
                " :label=\"item.value\" :disabled=\"item.disabled\" :border=\"item.border\" :size=\"item.size\" :checked=\"item.checked\" " +
                " :indeterminate=\"item.indeterminate\">"+labelField+"</el-checkbox"+(isButton?"-button":"")+">" +
                "</el-checkbox-group>\n";
    }
}