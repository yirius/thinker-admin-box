package com.thinker.framework.framework.renders.page.plugins;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.abstracts.renders.AssemblyLayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerTab extends LayoutAbstract {

    public enum TabType {
        NORMAL(""), CARD("card"), BORDERCARD("border-card");

        String type;
        TabType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    @ComponentAttr
    private TabType type = TabType.CARD;

    @ComponentAttr(prevStr = ":")
    private Boolean closable;

    @ComponentAttr(prevStr = ":")
    private Boolean addable;

    @ComponentAttr(prevStr = ":")
    private Boolean editable;

    public enum TabPosition {
        TOP, RIGHT, BOTTOM, LEFT;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    @ComponentAttr
    private TabPosition tabPosition = TabPosition.TOP;

    @ComponentAttr(prevStr = ":")
    private Boolean stretch;

    /**
     * tab的内部内容
     */
    private List<ThinkerTabPane> panes = new ArrayList<>();

    public ThinkerTab tabPane(String label, String name, ThinkerTabPane.Closure closure) {
        ThinkerTabPane tabPane = initLayoutAbstract(new ThinkerTabPane(label, name));
        if(closure != null) closure.run(tabPane);
        panes.add(tabPane);
        return this;
    }

    // 活动的名称
    private String activeName;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        StrBuilder strBuilder = new StrBuilder();
        if(panes.size() > 0) {
            panes.forEach(thinkerTabPane -> strBuilder.append(thinkerTabPane.render()));
            if(Validator.isEmpty(activeName)) activeName = panes.get(0).getName();
        }
        PageParams.setSetupReturn(getLayoutId() + "_tab_activeName", "'"+activeName+"'");
        return "<el-tabs v-model=\""+getLayoutId()+"_tab_activeName\" "+PageParams.strComponentAttrs(this)+">"+strBuilder.toStringAndReset()+"</el-tabs>";
    }
}
