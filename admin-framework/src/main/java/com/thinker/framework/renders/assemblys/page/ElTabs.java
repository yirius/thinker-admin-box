package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.DefineComponent;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.bo.enums.ElTabsPosition;
import com.thinker.framework.renders.bo.enums.ElTabsType;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ElTabs extends RootRender {

    @ToRenderAttrs(isModelRefsValue = true)
    private String modelValue;

    @ToRenderAttrs
    private ElTabsType type;

    @ToRenderAttrs
    private Boolean closable;

    @ToRenderAttrs
    private Boolean addable;

    @ToRenderAttrs
    private Boolean editable;

    @ToRenderAttrs
    private ElTabsPosition tabPosition;

    @ToRenderAttrs
    private Boolean stretch;

    @ToRenderAttrs(isEval = true)
    private String beforeLeave;

    @ToRenderAttrs(isEval = true)
    private String onTabClick;

    @ToRenderAttrs(isEval = true)
    private String onTabChange;

    @ToRenderAttrs(isEval = true)
    private String onTabRemove;

    @ToRenderAttrs(isEval = true)
    private String onTabAdd;

    @ToRenderAttrs(isEval = true)
    private String onEdit;

    // 储存tab
    private List<ElTabPane> tabPanes = new ArrayList<>();

    // 添加一个tabPane
    public ElTabPane addTabPane(String label, String name, RunClosure<ElTabPane> elTabPaneRunClosure) {
        ElTabPane elTabPane = new ElTabPane().setLabel(label).setName(name);
        tabPanes.add(elTabPane);
        if(elTabPaneRunClosure != null) {
            elTabPaneRunClosure.run(elTabPane);
        }
        return elTabPane;
    }

    @Override
    public void beforeRender() {
        setComponent("ElTabs");

        setModelValue(DefineComponent.getRenderId() + "_tabs_current");

        DefineComponent.setModelValue(DefineComponent.getRenderId() + "_tabs_current", tabPanes.size() == 0 ? "" : tabPanes.get(0).getName());

        getChildren().addAll(tabPanes);
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }

}
