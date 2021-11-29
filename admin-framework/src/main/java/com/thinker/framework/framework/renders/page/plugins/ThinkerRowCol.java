package com.thinker.framework.framework.renders.page.plugins;

import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerRowCol extends LayoutAbstract {

    @ComponentAttr(prevStr = ":")
    private Integer span = 12;

    @ComponentAttr(prevStr = ":")
    private Integer offset;

    @ComponentAttr(prevStr = ":")
    private Integer push;

    @ComponentAttr(prevStr = ":")
    private Integer pull;

    @ComponentAttr(prevStr = ":")
    private Integer xs;

    @ComponentAttr(prevStr = ":")
    private Integer sm;

    @ComponentAttr(prevStr = ":")
    private Integer md;

    @ComponentAttr(prevStr = ":")
    private Integer lg;

    @ComponentAttr(prevStr = ":")
    private Integer xl;

    private String content;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        return "<el-col "+ PageParams.strComponentAttrs(this) +">"+content+"</el-col>";
    }
}
