package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
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
public class Tree extends AssemblyAbstract {

    @ComponentAttr(prevStr = ":")
    private Boolean showCheckbox = true;

    @ComponentAttr
    private String nodeKey = "value";

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<LabelValue> data = new ArrayList<>();

    public Tree(String prop, String label) {
        super(prop, label);
    }

    private String check = "";

    public String getCheckStr() {
        PageParams.setMethods(getLayoutId() + "_check", "(data) {" +
                "   let refTree = this.$refs." + getLayoutId()+"_"+getProp()+";" +
                "   let allValues = [...refTree.getCheckedKeys(), ...refTree.getHalfCheckedKeys()];" +
                "   this." + getLayoutId() + "_formValue."+getProp()+" = allValues;" +
                "\n" + check +
                "}");

        return "@check=\""+getLayoutId()+"_check\"";
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

        // 设置计算属性，否则参数永远都是全选
        PageParams.setComputed(getLayoutId()+"_"+getProp()+"_defaultCheckedKeys", "() {" +
                "   var refTree = this.$refs." + getLayoutId()+"_"+getProp()+";" +
                "   if(refTree) {" +
                "       const nodeIds = toRaw(unref(this." + getLayoutId()+"_formValue."+getProp() + "));" +
                "       let treeSelectedKeys = [];" +
                "       for(var i in nodeIds) {" +
                "           if(refTree.getNode(nodeIds[i]).childNodes.length == 0) {" +
                "               treeSelectedKeys.push(nodeIds[i]);" +
                "           }" +
                "       }" +
                "       return treeSelectedKeys;" +
                "   }" +
                "   return this." + getLayoutId()+"_formValue."+getProp() + ";" +
                "}");

        return "<el-tree ref=\""+getLayoutId()+"_"+getProp()+"\" "+getCheckStr()+" "+ PageParams.strComponentAttrs(this) +
                " :default-checked-keys=\""+getLayoutId()+"_"+getProp()+"_defaultCheckedKeys\" />";
    }
}
