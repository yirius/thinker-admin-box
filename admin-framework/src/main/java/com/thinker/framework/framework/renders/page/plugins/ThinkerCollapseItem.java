package com.thinker.framework.framework.renders.page.plugins;

import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ThinkerCollapseItem extends LayoutAbstract {

    @ComponentAttr
    private String name;

    @ComponentAttr
    private String title;

    @ComponentAttr(prevStr = ":")
    private Boolean disabled;

    private String content;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        return "<el-collapse-item "+ PageParams.strComponentAttrs(this) +">"+content+"</el-collapse-item>";
    }
}
