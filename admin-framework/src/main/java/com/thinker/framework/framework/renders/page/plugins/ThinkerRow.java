package com.thinker.framework.framework.renders.page.plugins;

import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.FlexAlign;
import com.thinker.framework.framework.renders.enums.FlexJustify;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerRow extends LayoutAbstract {

    @ComponentAttr(prevStr = ":")
    private Integer gutter;

    @ComponentAttr
    private FlexJustify justify;

    @ComponentAttr
    private FlexAlign align;

    private String content;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        return "<el-row "+ PageParams.strComponentAttrs(this) +">"+content+"</el-row>";
    }
}
