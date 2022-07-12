package com.thinker.framework.admin.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.admin.entity.TkGroups;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.mapper.TkGroupsMapper;
import com.thinker.framework.admin.serviceimpl.TkGroupsImpl;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.aspect.logs.ThinkerRedisLogAspect;
import com.thinker.framework.framework.aspect.logs.ThinkerTableLogAspect;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.abstracts.TokenAbstract;
import com.thinker.framework.token.extend.ThinkerRestful;
import com.thinker.framework.token.factory.TokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/restful/thinker/system/roles")
public class TkGroupsRestful extends ThinkerRestful<TkGroupsMapper, TkGroups> {

    public TkGroupsRestful() {
        setUseTable(TkGroupsImpl.class);

        setParserWrapper(thinkerWrapper -> {
            Dict tokenInfo = TokenFactory.loadToken().checkLogin();
            Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
            int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

            if(accessType != 0) {
                thinkerWrapper.eq("access_type", accessType);
                thinkerWrapper.eq("member_id", userId);
            }
        });
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
    public ThinkerResponse save(TkGroups entity, BindingResult bindingResult) {
        return super.save(entity, bindingResult);
    }

    @Override
    public void _beforeSave(TkGroups entity) {
        TokenAbstract tokenAbstract = TokenFactory.loadToken();
        if(!tokenAbstract.isLogin()) {
            throw new ThinkerException("登录后才能进行角色添加");
        }

        // 如果是第一次添加，就设置参数
        if(Validator.isEmpty(entity.getId())) {
            entity.setAccessType(tokenAbstract.getLoginType());
            entity.setMemberId(Long.parseLong(String.valueOf(tokenAbstract.getLoginId())));
        }

        if(Validator.isNotEmpty(entity.getRuleIds())) {
            List<Long> ruleIds = JSON.parseArray(entity.getRuleIds(), Long.class);
            if(ruleIds.size() == 0) {
                throw new ThinkerException("规则未选择");
            }

            // 需要加入所有的parentid
            List<Long> parentIds = findParentIds(ruleIds);
            for (int i = 0; i < parentIds.size(); i++) {
                if(!ruleIds.contains(parentIds.get(i))) {
                    ruleIds.add(parentIds.get(i));
                }
            }

            ruleIds.sort(Comparator.comparingInt(Long::intValue));

            entity.setRuleIds(JSON.toJSONString(ruleIds));
        }
    }

    /**
     * 找到上级菜单
     * @param ruleIds
     * @return
     */
    private List<Long> findParentIds(List<Long> ruleIds) {
        List<Long> parentIds = SpringContext.getBean(TkRulesImpl.class)
                .query().in("id", ruleIds)
                .ne("parent_id", 0).select("parent_id").list().stream().map(TkRules::getParentId).collect(Collectors.toList());

        if(parentIds.size() > 0) {
            parentIds.addAll(findParentIds(parentIds));
        }

        return parentIds;
    }

    @Override
    @ThinkerTableLogAspect
    public ThinkerResponse delete(String ids, String password) {
        return super.delete(ids, password);
    }

    @Override
    public Object _afterRead(TkGroups entity) {
        Map<String, Object> map = BeanUtil.beanToMap(entity);

        // 计算ruleid，去掉顶层parentid
        List<Long> ruleIds = Validator.isEmpty(entity.getRuleIds()) ? new ArrayList<>() : JSON.parseArray(entity.getRuleIds(), Long.class);

        // 找到所有的parentid，做去除
        List<TkRules> tkRulesList = SpringContext.getBean(TkRulesImpl.class).query().in("id", ruleIds).select("parent_id").list();
        tkRulesList.forEach(tkRules -> {
            if(tkRules.getParentId() != 0) {
                ruleIds.remove(tkRules.getParentId());
            }
        });

        map.put("ruleIds", ruleIds);
        return map;
    }

    @Override
    public void _afterSave(TkGroups entity, boolean isUpdate) {
        // 用户菜单可能变化，直接删除
        ThinkerAdmin.redis().rm("ADMIN_ALL_USER_MENUS");
        // 用户角色可能变化，直接删除
        ThinkerAdmin.redis().rm("ADMIN_USER_ROLES");
    }

    @Override
    public List<String> _beforeDelete(String ids) {
        List<String> deleteIds = Arrays.asList(ids.split(","));
        if(deleteIds.contains("1")) {
            throw new ThinkerException("message.thinker.admin.notDelete", "无法删除初始超级管理员", 206, Dict.create().set("reason", "超级管理员id=1"));
        }
        return deleteIds;
    }

    /**
     * 获取所有规则列表
     * @return
     */
    @RequestMapping(value = "/getRuleIds")
    public ThinkerResponse getRuleIds() {
        return new ThinkerResponse().data(
                parseLabelValue(
                        TreeUtil.build(
                                SpringContext.getBean(TkRulesImpl.class)
                                        .query().orderByAsc("parent_id").list()
                                        .stream()
                                        .map(tkRules -> new TreeNode<>(tkRules.getId(), tkRules.getParentId(), tkRules.getTitle(), tkRules.getWeight()))
                                        .collect(Collectors.toList()),
                                0L
                        ))
        ).success();
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
}
