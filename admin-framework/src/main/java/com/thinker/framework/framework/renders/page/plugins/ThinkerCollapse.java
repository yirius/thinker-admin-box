package com.thinker.framework.framework.renders.page.plugins;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ThinkerCollapse extends LayoutAbstract {

    // 使用的名称
    private String modelName;

    @ComponentAttr(prevStr = ":")
    private Boolean accordion;

    // 所有的子菜单
    @Setter(AccessLevel.NONE)
    private List<ThinkerCollapseItem> collapseItemList = new ArrayList<>();

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        List<String> itemNames = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        collapseItemList.forEach(thinkerCollapseItem -> {
            itemNames.add(thinkerCollapseItem.getName());
            contents.add((String) thinkerCollapseItem.render());
        });

        if(itemNames.size() > 0) {
            if(accordion) {
                PageParams.createRef(getLayoutId()+"_"+modelName, "'"+itemNames.get(0)+"'");
            } else {
                PageParams.createRef(getLayoutId()+"_"+modelName, JSON.toJSONString(itemNames));
            }
        } else {
            if(accordion) {
                PageParams.createRef(getLayoutId()+"_"+modelName, "''");
            } else {
                PageParams.createRef(getLayoutId()+"_"+modelName, "[]");
            }
        }

        return "<el-collapse v-model=\""+getLayoutId()+"_"+modelName+"\" "+ PageParams.strComponentAttrs(this) +">"+ StrUtil.join("", contents) +"</el-collapse>";
    }
}
