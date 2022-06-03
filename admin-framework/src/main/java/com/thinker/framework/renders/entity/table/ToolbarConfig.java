package com.thinker.framework.renders.entity.table;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.ElButton;
import com.thinker.framework.renders.assemblys.table.VxeColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ToolbarConfig {
    @ToRenderAttrs
    private String size;

    @ToRenderAttrs
    private Boolean loading;

    @ToRenderAttrs
    private Boolean perfect;

    @ToRenderAttrs(isEval = true)
    private String className;

    @ToRenderAttrs
    private Boolean importAttr = false;

    @ToRenderAttrs
    private Boolean export = true;

    @ToRenderAttrs
    private Boolean print = true;

    @ToRenderAttrs
    private Boolean refresh = true;

    @ToRenderAttrs
    private Boolean zoom = false;

    @ToRenderAttrs
    private Boolean custom = true;

//    @ToRenderAttrs(isSlot = true)
//    private List<ElButton> buttons;

    @ToRenderAttrs
    private Boolean enabled;

    // 写一下渲染
    @ToRenderAttrs(isJsonObject = true)
    private ToolbarConfigSlots slots;

    public ToolbarConfig button(RunClosure<ElButton> buttonRun) {
        if(slots == null) slots = new ToolbarConfigSlots();
        ElButton button = new ElButton();
        slots.getButtons().add(button);
        if(buttonRun != null) {
            buttonRun.run(button);
        }
        return this;
    }

    public ToolbarConfig add() {
        return add(null);
    }

    public ToolbarConfig add(RunClosure<ElButton> elButtonRunClosure) {
        if(slots == null) slots = new ToolbarConfigSlots();
        ElButton elButton = new ElButton().setLabel("添加").setType("primary")
                .setIcon("CirclePlus").setSize("small").setOnClick("(e) => { vm.emit('openAddLayer'); }");
        slots.getButtons().add(elButton);
        if(elButtonRunClosure != null) {
            elButtonRunClosure.run(elButton);
        }
        return this;
    }

    public ToolbarConfig delete() {
        return delete(null);
    }

    public ToolbarConfig delete(RunClosure<ElButton> elButtonRunClosure) {
        if(slots == null) slots = new ToolbarConfigSlots();
        ElButton elButton = new ElButton().setLabel("删除").setType("danger")
                .setIcon("CircleClose").setSize("small");

        elButton.setOnClick("(e) => { " +
                "   _elementPlus.ElMessageBox.prompt('请输入密码，验证后删除', '密码验证', {" +
                "       cancelButtonText: '取消'," +
                "       confirmButtonText: '确认'," +
                "       inputType: 'password'," +
                "       inputPlaceholder: '请输入当前账号登录密码'" +
                "   }).then(value => { " +
                "       if(value.action=='confirm') {" +
                "           vxeGrid.value.commitProxy({extInfo: {password: value.value}, code: 'delete'});" +
                "       } " +
                "   }) " +
                "}");

        slots.getButtons().add(elButton);
        if(elButtonRunClosure != null) {
            elButtonRunClosure.run(elButton);
        }
        return this;
    }
}
