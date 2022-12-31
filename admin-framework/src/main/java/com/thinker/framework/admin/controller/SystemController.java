package com.thinker.framework.admin.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.serviceimpl.TkGroupsImpl;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.renders.DefineComponent;
import com.thinker.framework.renders.assemblys.form.plugins.ElUpload;
import com.thinker.framework.renders.assemblys.page.ElPopconfirm;
import com.thinker.framework.renders.entity.enums.InputTypeEnum;
import com.thinker.framework.renders.entity.table.CellRender;
import com.thinker.framework.renders.entity.table.EditRuleItem;
import com.thinker.framework.renders.entity.table.TreeConfig;
import com.thinker.framework.token.abstracts.TokenAbstract;
import com.thinker.framework.token.extend.ThinkerController;
import com.thinker.framework.token.factory.TokenFactory;
import com.thinker.framework.token.util.JwtUtil;
import com.thinker.framework.token.util.ThreadTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/thinker/system")
public class SystemController extends ThinkerController {

    @RequestMapping(value = "/rules")
    public ThinkerResponse rules()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/rules").setEditUrl("/thinker/system/rulesEdit");

            thinkerTable.seq().setTreeNode(true).setWidth("120px");
            thinkerTable.column("id", "id");
            thinkerTable.column("title", "中文名称");
            thinkerTable.column("titleEn", "英文名称");
            thinkerTable.column("name", "语言名称");
            // 渲染菜单
            renderOperateColumn(thinkerTable, "操作", null, null, null, ElPopconfirm::deleteUsePassword);

            thinkerTable.setCheckboxConfig(null);
            thinkerTable.setTreeConfig(new TreeConfig());

            thinkerTable.getPagerConfig().setPageSize(10000);
            thinkerTable.getPagerConfig().setPageSizes(Collections.singletonList(1000));

        }).page();
    }

    @RequestMapping(value = "/rulesEdit")
    public ThinkerResponse rulesEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            DefineComponent.addRenderDataReady(
                    "if(nextProp&&nextProp.viewBasePageRef&&nextProp.viewBasePageRef.value) {" +
                            "   var componentRender = nextProp.viewBasePageRef.value.findRender('component');" +
                            "   if(componentRender) { componentRender.attrs.disabled = nextProp.formValue.component == 'Layout'; }" +
                            "   var isLayoutRender = nextProp.viewBasePageRef.value.findRender('isLayout');" +
                            "   if(isLayoutRender) { isLayoutRender.attrs.disabled = nextProp.formValue.component == 'Layout'; }" +
                            "   var parentIdRender = nextProp.viewBasePageRef.value.findRender('parentId');" +
                            "   if(parentIdRender) { parentIdRender.attrs.disabled = nextProp.formValue.parentId == 0; }" +
                            "}");

            thinkerForm.tabPane("基础参数", "baseFields", thinkerTabPane -> {
                thinkerTabPane.openRow();

                thinkerTabPane.input("parentId", "上级ID").setDisabled(false).openRowCol().setXs(24);
                thinkerTabPane.input("name", "短参数名").openRowCol().setXs(24);
                thinkerTabPane.input("title", "中文名称").openRowCol().setXs(24);
                thinkerTabPane.input("titleEn", "英文名称").openRowCol().setXs(24);
                thinkerTabPane.input("path", "显示路径").openRowCol().setXs(24);
                thinkerTabPane.input("redirect", "重定向路径").openRowCol().setXs(24);

                thinkerTabPane.switchs("isLayout", "是否主组件").setDisabled(false)
                        .setOnChange("(val) => { " +
                                     "   props.modelRefsValue.component.value = val == 1 ? 'Layout' : '';" +
                                     "   var componentRender = fieldIdOperate.find('component');" +
                                     "   if(componentRender) { componentRender.attrs.disabled = val == 1; }" +
                                     "}"
                        ).openRowCol().setSpan(6).setXs(12);

                thinkerTabPane.switchs("isRouter", "是否路由").openRowCol().setSpan(6).setXs(12);

                thinkerTabPane.input("component", "组件地址").setDisabled(false).openRowCol().setSpan(12).setXs(24);
            });

            thinkerForm.tabPane("可选参数", "chooseFields", thinkerTabPane -> {
                thinkerTabPane.openRow();

                thinkerTabPane.input("icon", "图标选择").openRowCol().setSpan(24);

                thinkerTabPane.upload("iconPic", "默认图标").setLimit(1).openRowCol().setSpan(12);
                thinkerTabPane.upload("iconSelectedPic", "选中图标").setLimit(1).openRowCol().setSpan(12);

                thinkerTabPane.input("weight", "权重(大数靠后)").setType(InputTypeEnum.NUMBER).openRowCol().setSpan(24);

                thinkerTabPane.switchs("alwayShow", "持续侧边打开").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("cache", "是否缓存").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("hideTab", "TAB不展示").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("hideMenu", "菜单不展示").openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("hideClose", "不可关闭")
                        .setBeforeChange("() => { if(props.useIdKey==1||props.useIdKey==2) {_elementPlus.ElMessage.info('初始仪表无法设置可关闭')} return !(props.useIdKey==1||props.useIdKey==2); }")
                        .openRowCol().setSpan(8).setXs(12);
                thinkerTabPane.switchs("isRender", "JSON渲染").openRowCol().setSpan(8).setXs(12);
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

        }).setApi("/restful/thinker/system/rules").page();
    }

    /**
     * 角色管理
     * @return
     */
    @RequestMapping(value = "/roles")
    public ThinkerResponse roles()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/roles").setEditUrl("/thinker/system/rolesEdit");

            thinkerTable.checkbox();
            thinkerTable.column("id", "id");
            thinkerTable.column("title", "名称");

            // 如果是超级管理员，需要分配权限，能够看到是谁添加的
            TokenAbstract tokenAbstract = TokenFactory.loadToken();
            if(tokenAbstract.isLogin() && tokenAbstract.getLoginType() == 0) {
                thinkerTable.column("accessType", "权限类型");
                thinkerTable.column("memberId", "归属用户");
            }

            // 渲染菜单
            renderOperateColumn(thinkerTable, "操作", null, null, null, ElPopconfirm::deleteUsePassword);
        }).page();
    }

    @RequestMapping(value = "/rolesEdit")
    public ThinkerResponse rolesEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            thinkerForm.input("title", "组别名称");
            thinkerForm.input("name", "英文代号");

            Dict tokenInfo = TokenFactory.loadToken().checkLogin();
            Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
            int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

            QueryChainWrapper<TkRules> queryChainWrapper = SpringContext.getBean(TkRulesImpl.class).query().orderByAsc("parent_id");

            if(!(accessType == 0 && userId == 1)) {
                List<String> ruleIds = ThreadTokenUtil.getUserRuleIds(
                        Long.parseLong(String.valueOf(TokenFactory.loadToken().getLoginId())),
                        TokenFactory.loadToken().getLoginType()
                );

                queryChainWrapper.in("id", ruleIds);
            }

            thinkerForm.tree("ruleIds", "可用规则").setData(parseLabelValue(
                    TreeUtil.build(
                            queryChainWrapper.list()
                                    .stream()
                                    .map(tkRules -> new TreeNode<>(tkRules.getId(), tkRules.getParentId(), tkRules.getTitle(), tkRules.getWeight()))
                                    .collect(Collectors.toList()),
                            0L
                    ))).setShowCheckbox(true);

            thinkerForm.switchs("status", "角色状态");
        }).setApi("/restful/thinker/system/roles").page();
    }

    /**
     * 计算所有的规则
     * @param treeNodes
     * @return
     */
    private List<LabelValue> parseLabelValue(List<Tree<Long>> treeNodes) {
        return treeNodes.stream().map(nodeItem -> {
            if(nodeItem.getChildren() != null && nodeItem.getChildren().size() > 0) {
                return LabelValue.create(nodeItem.getName().toString(), nodeItem.getId()).set("children", parseLabelValue(nodeItem.getChildren()));
            }

            return LabelValue.create(nodeItem.getName().toString(), nodeItem.getId());
        }).collect(Collectors.toList());
    }

    /**
     * 成员管理
     * @return
     */
    @RequestMapping(value = "/members")
    public ThinkerResponse members()  {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/system/members").setEditUrl("/thinker/system/membersEdit");

            thinkerTable.search(thinkerForm -> {
                thinkerForm.input("username", "账户名");
                thinkerForm.input("phone", "手机号");
                thinkerForm.select("status", "状态").addOptions(quickLabelValues(
                        new Object[]{"全部", ""},
                        new Object[]{"可使用", 1},
                        new Object[]{"不可用", 0}
                )).setPlaceholder("--请选择状态--");
            });

            thinkerTable.checkbox();
            thinkerTable.column("username", "账户名").setMinWidth("120px");
            thinkerTable.column("phone", "手机号").setMinWidth("120px").setSortable(true);
            thinkerTable.column("realname", "真实姓名").setMinWidth("120px");
            // 渲染菜单
            renderOperateColumn(thinkerTable, "操作", null, null, null, ElPopconfirm::deleteUsePassword);
        }).page();
    }

    @RequestMapping(value = "/membersEdit")
    public ThinkerResponse membersEdit() {
        return ThinkerAdmin.form(thinkerForm -> {
            DefineComponent.addRenderReady("if(props.useIdKey && props.useIdKey == 1) {" +
                    "   renderValue.render.children[0].children[5].children[0].attrs.disabled = true;" +
                    "   renderValue.render.children[0].children[6].children[0].attrs.disabled = true;" +
                    "} else {" +
                    "   renderValue.render.children[0].children[5].children[0].attrs.disabled = false;" +
                    "   renderValue.render.children[0].children[6].children[0].attrs.disabled = false;" +
                    "}");

            thinkerForm.input("username", "账户名");
            thinkerForm.input("phone", "手机号");
            thinkerForm.input("realname", "真实姓名");
            thinkerForm.input("password", "密码").setType(InputTypeEnum.PASSWORD);
            thinkerForm.input("remarks", "备注").setType(InputTypeEnum.TEXTAREA);

            Dict tokenInfo = TokenFactory.loadToken().checkLogin();

            thinkerForm.select("groupIds", "对应角色组").setOptions(
                    SpringContext.getBean(TkGroupsImpl.class).getCanUseGroups(
                            tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey()),
                            tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey())
                    )
            ).setMultiple(true).setDisabled(false);

            thinkerForm.switchs("status", "角色状态").setDisabled(false);
        }).setApi("/restful/thinker/system/members").page();
    }
}
