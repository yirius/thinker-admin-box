package com.thinker.framework.framework.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.token.rules.MetaData;
import com.thinker.framework.token.util.ThreadTokenUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CacheUtil {
    @SuppressWarnings("unchecked")
    public static List<TkRules> getAdminRules() {
        List<TkRules> tkRulesList = (List<TkRules>) ThinkerAdmin.redis().get("ADMIN_RULES", null);
        if(tkRulesList == null) {
            tkRulesList = SpringContext.getBean(TkRulesImpl.class).query().list();

            ThinkerAdmin.redis().set("ADMIN_RULES", tkRulesList);
        }

        return tkRulesList;
    }

    /**
     * 获取到规则对应的map, key-> id, value-> component
     * @return
     */
    public static Map<String, String> getRuleComponentsMap() {
        return getAdminRules().stream().collect(Collectors.toMap(
                tkRules -> String.valueOf(tkRules.getId()),
                tkRules -> Validator.isNotEmpty(tkRules.getComponent()) ? tkRules.getComponent() : ""
        ));
    }

    /**
     * 获取到用户的菜单
     * @param userId
     * @param accessType
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static List<Tree<Long>> getUserMenu(Long userId, int accessType) {
        String redisSuffixName = "";
        // 存在配置
        if(ThinkerAdmin.properties().getToken().getRuleIndex() != null) {
            // 0默认不读取，其他的读取
            if(!(ThinkerAdmin.properties().getToken().getRuleIndex().size() == 1 && ThinkerAdmin.properties().getToken().getRuleIndex().get(0).equals(0))) {
                redisSuffixName = "_" + StrUtil.join("_", ThinkerAdmin.properties().getToken().getRuleIndex());
            }
        }

        List<Tree<Long>> treeList = (List<Tree<Long>>) ThinkerAdmin.redis().hashGet("ADMIN_ALL_USER_MENUS", userId+"_"+accessType + redisSuffixName);

        if(treeList == null) {
            List<String> userRuleIds = ThreadTokenUtil.getUserRuleIds(userId, accessType);
            if(userRuleIds.size() > 0) {
                // 根据ruleIndex做个判断，找不同的menu
                QueryChainWrapper<TkRules> tkRulesQueryChainWrapper = SpringContext.getBean(TkRulesImpl.class)
                        .query().orderByAsc("parent_id,weight").in("id", userRuleIds)
                        .eq("is_router", 1);

                // 存在配置
                if(ThinkerAdmin.properties().getToken().getRuleIndex() != null) {
                    // 0默认不读取，其他的读取
                    if(!(ThinkerAdmin.properties().getToken().getRuleIndex().size() == 1 && ThinkerAdmin.properties().getToken().getRuleIndex().get(0).equals(0))) {
                        tkRulesQueryChainWrapper.in("rule_index", ThinkerAdmin.properties().getToken().getRuleIndex());
                    }
                }

                // 找到所有的ID
                List<TreeNode<Long>> nodeList = tkRulesQueryChainWrapper.list().stream().map(tkRules -> {
                            tkRules.setTitle("message.menu." + tkRules.getName());

                            if(tkRules.getIconPic() != null && tkRules.getIconPic().equals("[]")) tkRules.setIconPic(null);
                            if(Validator.isNotEmpty(tkRules.getIconPic())) {
                                JSONArray jsonArray = JSON.parseArray(tkRules.getIconPic());
                                if(jsonArray.size() > 0) {
                                    tkRules.setIconPic(
                                            jsonArray.getJSONObject(0).getString("url")
                                    );
                                }
                            }

                            if(tkRules.getIconSelectedPic() != null && tkRules.getIconSelectedPic().equals("[]")) tkRules.setIconSelectedPic(null);
                            if(Validator.isNotEmpty(tkRules.getIconSelectedPic())) {
                                JSONArray jsonArray = JSON.parseArray(tkRules.getIconSelectedPic());
                                if(jsonArray.size() > 0) {
                                    tkRules.setIconSelectedPic(
                                            jsonArray.getJSONObject(0).getString("url")
                                    );
                                }
                            }

                            Dict dict = Dict.create()
                                    .set("meta", JSON.parseObject(JSON.toJSONString(tkRules), MetaData.class))
                                    .set("name", "message.menu." + tkRules.getName())
                                    .set("path", tkRules.getPath())
                                    .set("component", tkRules.getComponent())
                                    .set("redirect", tkRules.getRedirect());

                            return new TreeNode<>(tkRules.getId(), tkRules.getParentId(), tkRules.getName(), tkRules.getWeight())
                                    .setExtra(dict);
                        }).collect(Collectors.toList());

                treeList = TreeUtil.build(nodeList, 0L);

                ThinkerAdmin.redis().hashSet("ADMIN_ALL_USER_MENUS", userId+"_"+accessType + redisSuffixName, treeList);
            }
        }

        return treeList;
    }
}
