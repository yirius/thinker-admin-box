package com.thinker.framework.renders.assemblys;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerHtml extends RootRender {
    private String html;

    @Override
    public void beforeRender() {
        setComponent("html");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        renderResult.getExtInfo().set("html", html);
        return renderResult;
    }
}
