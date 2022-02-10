package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class InputRange extends AssemblyAbstract {

    // 左输入
    @Setter(AccessLevel.NONE)
    private Input firstInput;

    // 右输入
    @Setter(AccessLevel.NONE)
    private Input secondInput;

    // 中间的分隔线
    private String splitStr = "-";

    public InputRange(String prop, String label) {
        super(prop, label);

        firstInput = initLayoutAbstract(new Input(prop+"[0]", label));
        secondInput = initLayoutAbstract(new Input(prop+"[1]", label));
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

        firstInput.setLayoutId(getLayoutId());
        secondInput.setLayoutId(getLayoutId());

        return "<el-row><el-col :span='11'>"+firstInput.renderContent()+"</el-col><el-col :span='2'>"+splitStr+"</el-col><el-col :span='11'>"+secondInput.renderContent()+"</el-col></el-row>";
    }
}
