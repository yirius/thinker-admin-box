package com.thinker.framework.admin.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.thinker.framework.admin.serviceimpl.TkGroupsImpl;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.CheckboxItems;
import com.thinker.framework.framework.renders.bo.TinymceTemplate;
import com.thinker.framework.framework.renders.form.assemblys.Date;
import com.thinker.framework.framework.renders.form.assemblys.Input;
import com.thinker.framework.framework.renders.form.assemblys.Upload;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.token.extend.ThinkerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/thinker/system")
public class SystemController extends ThinkerController {

    @RequestMapping(value = "/rules.vue")
    public String rules()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/rules").setEditUrl("/thinker/system/rulesEdit.vue");

            thinkerTable.column("id", "id");
            thinkerTable.column("title", "中文名称");
            thinkerTable.column("titleEn", "英文名称");
            thinkerTable.column("name", "语言名称").openSortable();
            thinkerTable.column("op", "操作").edit().delete().setWidth("180px");

            thinkerTable.toolbar().add().delete().xlsx().defaultTools();

            thinkerTable.setDefaultExpandAll(true).setPageSizes(Arrays.asList(100,500,1000)).getPage().setSize(500);

        }).page().toString();
    }

    @RequestMapping(value = "/rulesEdit.vue")
    public String rulesEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            thinkerForm.setParseSetupData(
                    "if("+thinkerForm.getLayoutId() + "_formValue.value.component == 'Layout') {" +
                    "   "+ thinkerForm.getLayoutId() + "_component_disabled.value = true;" +
                    "   "+ thinkerForm.getLayoutId() + "_isLayout_disabled.value = true;" +
                    "}" + "if("+thinkerForm.getLayoutId() + "_formValue.value.parentId == 0) {" +
                            "   "+ thinkerForm.getLayoutId() + "_parentId_disabled.value = true;" +
                            "}");

            thinkerForm.tabPane("基础参数", "baseFields", thinkerTabPane -> {
                thinkerTabPane.openRow();

                thinkerTabPane.input("parentId", "上级ID").createDisabled().openRowCol().setXs(24);
                thinkerTabPane.input("name", "短参数名").openRowCol().setXs(24);
                thinkerTabPane.input("title", "中文名称").openRowCol().setXs(24);
                thinkerTabPane.input("titleEn", "英文名称").openRowCol().setXs(24);
                thinkerTabPane.input("path", "显示路径").openRowCol().setXs(24);
                thinkerTabPane.input("redirect", "重定向路径").openRowCol().setXs(24);

                thinkerTabPane.switchs("isLayout", "是否主组件")
                        .setChange(
                                "this."+ thinkerForm.getLayoutId() + "_formValue.component = 'Layout';" +
                                "this."+ thinkerForm.getLayoutId() + "_component_disabled = val == 1;"
                        ).createDisabled().openRowCol().setSpan(6).setXs(12);

                thinkerTabPane.switchs("isRouter", "是否路由").openRowCol().setSpan(6).setXs(12);

                thinkerTabPane.input("component", "组件地址").createDisabled().openRowCol().setSpan(12).setXs(24);
            });

            thinkerForm.tabPane("可选参数", "chooseFields", thinkerTabPane -> {
                thinkerTabPane.openRow();
                thinkerTabPane.input("icon", "图标选择").openRowCol().setSpan(24);
                thinkerTabPane.input("weight", "权重(越大越靠后)").setType(Input.InputType.NUMBER).openRowCol().setSpan(24);

                thinkerTabPane.switchs("alwayShow", "持续侧边打开").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("cache", "是否缓存").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("hideTab", "TAB不展示").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("hideMenu", "菜单不展示").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("hideClose", "不可关闭")
                        .setBeforeChange("if(this.layer.rowIdKey==1 || this.layer.rowIdKey==2) {ElMessage.info('初始仪表无法设置可关闭')} return !(this.layer.rowIdKey==1 || this.layer.rowIdKey==2);")
                        .openRowCol().setSpan(8).setXs(12);
            });

            thinkerForm.tabPane("下级菜单", "subChildrens", thinkerTabPane -> {
                thinkerTabPane.openRow();
                thinkerTabPane.switchs("needRestful", "添加restful").openRowCol().setXs(24);
                thinkerTabPane.switchs("needEdit", "添加编辑界面").openRowCol().setXs(24);
                thinkerTabPane.switchs("needAddBtn", "添加新增按钮").openRowCol().setXs(24);
                thinkerTabPane.switchs("needAllDelete", "添加批量删除").openRowCol().setXs(24);
                thinkerTabPane.switchs("needEditBtn", "添加修改按钮").openRowCol().setXs(24);
                thinkerTabPane.switchs("needDeleteBtn", "添加删除按钮").openRowCol().setXs(24);
            });

        }).setSubmitUrl("/restful/thinker/system/rules").page().toString();
    }

    /**
     * 角色管理
     * @return
     */
    @RequestMapping(value = "/roles.vue")
    public String roles()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/roles").setEditUrl("/thinker/system/rolesEdit.vue");

            thinkerTable.column("id", "id");
            thinkerTable.column("title", "名称");
            thinkerTable.column("op", "操作").edit().delete(button -> {
                button.openPopConfirm().confirmDeleteUsePassword(thinkerTable.getLayoutId(), "id", thinkerTable.getApi());
            }).setWidth("180px");

            thinkerTable.toolbar().add().delete(button -> {
                button.openPopConfirm().confirmDeleteUsePassword(thinkerTable.getLayoutId(), "id", thinkerTable.getApi());
            }).defaultTools();
        }).page().toString();
    }

    @RequestMapping(value = "/rolesEdit.vue")
    public String rolesEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            thinkerForm.input("title", "组别名称");
            thinkerForm.input("name", "英文代号");

            thinkerForm.tree("ruleIds", "可用规则");
            PageParams.setSetupSuffixScript(
                    "getRequest('/restful/thinker/system/roles/getRuleIds', {}).then(response => {" +
                            "   "+thinkerForm.getLayoutId()+"_ruleIds_data.value = response.data;" +
                            "});"
            );

            thinkerForm.switchs("status", "角色状态");

        }).setSubmitUrl("/restful/thinker/system/roles").page().toString();
    }

    /**
     * 成员管理
     * @return
     */
    @RequestMapping(value = "/members.vue")
    public String members()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/members").setEditUrl("/thinker/system/membersEdit.vue");

            thinkerTable.column("id", "id");
            thinkerTable.column("username", "账户名");
            thinkerTable.column("phone", "手机号");
            thinkerTable.column("realname", "真实姓名");
            thinkerTable.column("op", "操作").edit().delete(button -> {
                button.openPopConfirm().confirmDeleteUsePassword(thinkerTable.getLayoutId(), "id", thinkerTable.getApi());
            }).setWidth("180px");

            thinkerTable.toolbar().add().delete(button -> {
                button.openPopConfirm().confirmDeleteUsePassword(thinkerTable.getLayoutId(), "id", thinkerTable.getApi());
            }).defaultTools();
        }).page().toString();
    }

    @RequestMapping(value = "/membersEdit.vue")
    public String membersEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            thinkerForm.setParseSetupData("if(props.layer.rowIdKey&&props.layer.rowIdKey==1) {" +
                    "   " + thinkerForm.getLayoutId() + "_groupIds_disabled.value = true;" +
                    "   " + thinkerForm.getLayoutId() + "_status_disabled.value = true;" +
                    "}");

            thinkerForm.input("username", "账户名");
            thinkerForm.input("phone", "手机号");
            thinkerForm.input("realname", "真实姓名");
            thinkerForm.input("password", "密码").setType(Input.InputType.PASSWORD);
            thinkerForm.input("remarks", "备注").setType(Input.InputType.TEXTAREA);

            thinkerForm.select("groupIds", "对应角色组").setOptions(
                    SpringContext.getBean(TkGroupsImpl.class).query().eq("status", 1).list()
                            .stream().map(tkGroups -> LabelValue.create(tkGroups.getTitle(), tkGroups.getId()))
                            .collect(Collectors.toList())
            ).setMultiple(true).createDisabled();

            thinkerForm.switchs("status", "角色状态").createDisabled();
        }).setSubmitUrl("/restful/thinker/system/members").page().toString();
    }
}
