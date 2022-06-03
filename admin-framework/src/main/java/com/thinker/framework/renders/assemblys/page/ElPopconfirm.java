package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.aspects.ToRenderSlots;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElPopconfirm extends RootRender {

    @ToRenderAttrs
    private String title;

    @ToRenderAttrs
    private String confirmButtonText;

    @ToRenderAttrs
    private String cancelButtonText;

    @ToRenderAttrs
    private String confirmButtonType;

    @ToRenderAttrs
    private String cancelButtonType;

    @ToRenderAttrs
    private String icon;

    @ToRenderAttrs
    private String iconColor;

    @ToRenderAttrs
    private Boolean hideIcon;

    @ToRenderAttrs
    private Boolean teleported;

    @ToRenderAttrs
    private Boolean persistent;

    @ToRenderAttrs(isEval = true)
    private String onConfirm;

    @ToRenderAttrs(isEval = true)
    private String onCancel;

    public ElPopconfirm delete() {
        setTitle("是否确认删除该行?");
        setOnConfirm("(e) => { vxeGrid.value.proxyConfig.ajax.delete({$grid: vxeGrid, body: {removeRecords: [slotData.row]}, button: null, code: 'delete'}); }");
        return this;
    }

    public ElPopconfirm deleteUsePassword() {
        setTitle("是否确认删除该行?");
        setOnConfirm("(e) => { " +
                "   _elementPlus.ElMessageBox.prompt('请输入密码，验证后删除', '密码验证', {" +
                "       cancelButtonText: '取消'," +
                "       confirmButtonText: '确认'," +
                "       inputType: 'password'," +
                "       inputPlaceholder: '请输入当前账号登录密码'" +
                "   }).then(value => { " +
                "       if(value.action=='confirm') {" +
                "           vxeGrid.value.proxyConfig.ajax.delete({$grid: vxeGrid, body: {removeRecords: [slotData.row]}, button: {extInfo: {password: value.value}, code: 'delete'}, code: 'delete'});" +
                "       } " +
                "   }) " +
                "}");
        return this;
    }

    private List<RootRender> reference = new ArrayList<>();

    @Override
    public void beforeRender() {
        setComponent("ElPopconfirm");

        if(reference.size() > 0) {
            getSlots().put("reference", reference);
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }

}
