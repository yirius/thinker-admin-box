package com.thinker.framework.admin.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.mapper.TkRulesMapper;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.aspect.logs.ThinkerTableLogAspect;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.extend.ThinkerRestful;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/restful/thinker/system/rules")
public class TkRulesRestful extends ThinkerRestful<TkRulesMapper, TkRules> {

    public TkRulesRestful() {
        setUseTable(TkRulesImpl.class);
    }

    @Override
    public ThinkerResponse index() {
        List<TreeNode<Long>> nodeList = SpringContext.getBean(TkRulesImpl.class)
                .query().orderByAsc("parent_id").list()
                .stream().map(tkRules -> new TreeNode<>(tkRules.getId(), tkRules.getParentId(), tkRules.getName(), tkRules.getWeight())
                        .setExtra(BeanUtil.beanToMap(tkRules))).collect(Collectors.toList());

        return new ThinkerResponse().data(
                Dict.create().set("items", TreeUtil.build(nodeList, 0L)).set("total", nodeList.size())
        ).success();
    }

    /**
     * 保存
     *
     * @param entity
     * @param bindingResult
     * @return
     */
    @Override
    @ThinkerTableLogAspect
    public ThinkerResponse save(TkRules entity, BindingResult bindingResult) {
        return super.save(entity, bindingResult);
    }

    @Override
    @ThinkerTableLogAspect
    public ThinkerResponse delete(String ids, String password) {
        return super.delete(ids, password);
    }

    @Override
    public Object _afterRead(TkRules entity) {
        Map<String, Object> map = BeanUtil.beanToMap(entity);
        map.put("alwayShow", entity.getAlwayShow() ? 1 : 0);
        map.put("cache", entity.getCache() ? 1 : 0);
        map.put("hideTab", entity.getHideTab() ? 1 : 0);
        map.put("hideMenu", entity.getHideMenu() ? 1 : 0);
        map.put("hideClose", entity.getHideClose() ? 1 : 0);
        map.put("iconPic", Validator.isEmpty(entity.getIconPic()) ? new ArrayList<>() : JSON.parseArray(entity.getIconPic()));
        map.put("iconSelectedPic", Validator.isEmpty(entity.getIconSelectedPic()) ? new ArrayList<>() : JSON.parseArray(entity.getIconSelectedPic()));

        return map;
    }

    @Override
    public void _beforeSave(TkRules entity) {
        if(!entity.getComponent().equals("Layout") && !entity.getComponent().contains(".vue")) {
            if(entity.getIsRender() == 0) {
                entity.setComponent(entity.getComponent() + ".vue");
            }
        }
    }

    @Override
    public void _afterSave(TkRules entity, boolean isUpdate) {
        if(!isUpdate) {
            // 如果不是更新，那需要判断一下是否存在
            Dict reciveData = Dict.create();
            ThinkerAdmin.request().getRequest().getParameterMap().forEach((s, strings) -> {
                if(s.contains("need")) {
                    reciveData.set(s, strings[0]);
                }
            });

            List<TkRules> tkRulesList = new ArrayList<>();
            if(reciveData.getStr("needRestful").equals("1")) {
                TkRules restfulRule = new TkRules();
                restfulRule.setParentId(entity.getId())
                        .setName(entity.getName() + "Restful")
                        .setTitle(entity.getTitle() + "Restful").setTitleEn(entity.getTitleEn() + " Restful")
                        .setIsLayout(0).setIsRouter(0)
                        .setComponent("/restful" + entity.getComponent().replace(".vue", ""))
                        .setWeight(0L);
                tkRulesList.add(restfulRule);
            }

            if(reciveData.getStr("needEdit").equals("1")) {
                TkRules editRule = new TkRules();
                editRule.setParentId(entity.getId())
                        .setTitle(entity.getTitle() + "编辑").setTitleEn(entity.getTitleEn() + " Edit")
                        .setIsLayout(0).setIsRouter(0)
                        .setName(entity.getName() + "Edit").setPath(entity.getPath() + "Edit")
                        .setComponent(entity.getComponent().replace(".vue", "Edit.vue"))
                        .setWeight(1L);
                tkRulesList.add(editRule);
            }

            if(reciveData.getStr("needAddBtn").equals("1")) {
                tkRulesList.add(createButtonRule(entity, "新增", "Add", 2L));
            }

            if(reciveData.getStr("needAllDelete").equals("1")) {
                tkRulesList.add(createButtonRule(entity, "批量删除", "All Delete", 3L));
            }

            if(reciveData.getStr("needEditBtn").equals("1")) {
                tkRulesList.add(createButtonRule(entity, "编辑", "Edit", 4L));
            }

            if(reciveData.getStr("needDeleteBtn").equals("1")) {
                tkRulesList.add(createButtonRule(entity, "删除", "Delete", 5L));
            }

            getTableImpl().saveBatch(tkRulesList);
        }

        // 字典必然变化
        ThinkerAdmin.redis().rm("SYS_LANG");
        // 规则必然变化
        ThinkerAdmin.redis().rm("ADMIN_RULES");
        // 菜单必然变化
        ThinkerAdmin.redis().rm("ADMIN_ALL_USER_MENUS");
    }

    private TkRules createButtonRule(TkRules entity, String title, String titleEn, Long weight) {
        return new TkRules().setParentId(entity.getId())
                .setTitle(entity.getTitle() + title + "按钮").setTitleEn(entity.getTitleEn() + " "+titleEn+" Button")
                .setIsLayout(0).setIsRouter(0)
                .setName(entity.getName() + titleEn.replace(" ", "") + "Button")
                .setPageType(titleEn.equals("Add")?"addBtn":(titleEn.equals("Edit")?"editBtn":(titleEn.equals("Delete")?"delBtn":"allDelBtn")))
                .setWeight(weight);
    }

    @Override
    public void _afterDelete(List<String> ids) {
        boolean canDelete = true;
        for (int i = 0; i < ids.size(); i++) {
            if(Long.parseLong(ids.get(i)) < 33) {
                canDelete = false;
                break;
            }
        }
        if(!canDelete) {
            throw new ThinkerException("message.thinker.admin.notDelete", "密码为空，请填写", 206);
        }
    }
}
