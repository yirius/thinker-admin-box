package com.thinker.framework.framework.renders.page.plugins;

import com.thinker.framework.framework.abstracts.renders.AssemblyLayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerTabPane extends AssemblyLayoutAbstract {

    @ComponentAttr
    private String label;

    @ComponentAttr
    private String name;

    @ComponentAttr(prevStr = ":", isRef = true)
    private Boolean disabled;

    @ComponentAttr(prevStr = ":", isRef = true)
    private Boolean closable;

    @ComponentAttr(prevStr = ":")
    private Boolean lazy;

    public ThinkerTabPane(String label, String name) {
        setLabel(label);
        setName(name);
    }

    //使用闭包的形式
    public interface Closure {
        void run(ThinkerTabPane thinkerTabPane);
    }

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        // 判断一下是否需要渲染el-row
        return "<el-tab-pane " + PageParams.strComponentAttrs(this) + ">" + renderRow(getAssemblysRender()) + "</el-tab-pane>";
    }
}
