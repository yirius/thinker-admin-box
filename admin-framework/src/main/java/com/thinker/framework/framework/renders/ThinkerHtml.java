package com.thinker.framework.framework.renders;

import com.thinker.framework.framework.abstracts.LayoutAbstract;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
// 默认的渲染组件，可以直接写HTML语句
public class ThinkerHtml extends LayoutAbstract {

    private String html = "";

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        return this.html;
    }
}
