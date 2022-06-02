package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ElRow extends RootRender {

    public ElCol addCol() {
        ElCol elCol = new ElCol();
        children.add(elCol);
        return elCol;
    }

    @Override
    public void beforeRender() {
        setComponent("ElRow");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
