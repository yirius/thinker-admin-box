package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.DefineComponent;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormRender;
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
public class ElTabPane extends FormRender {

    @ToRenderAttrs
    private String label;

    @ToRenderAttrs
    private String name;

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs
    private Boolean closable;

    @ToRenderAttrs
    private Boolean lazy;

    // 是否打开
    private Boolean openRow;

    public ElTabPane openRow() {
        openRow = true;
        return this;
    }

    @Override
    public void beforeRender() {
        setComponent("ElTabPane");

        if(openRow != null && openRow) {
            ElRow elRow = new ElRow();
            elRow.setChildren(getChildren());
            List<RootRender> elRows = new ArrayList<>();
            elRows.add(elRow);
            setChildren(elRows);
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }

}
