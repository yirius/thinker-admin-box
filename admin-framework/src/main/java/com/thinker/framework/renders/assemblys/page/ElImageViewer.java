package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
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
public class ElImageViewer extends RootRender {

    private String modelValue;

    public ElImageViewer setModelValue(String modelValue) {
        this.modelValue = modelValue;
        this.onClose = "() => { props.modelRefsValue['"+modelValue+"_vIf'].value = false; };";
        return this;
    }

    @ToRenderAttrs(toModelValue = true)
    private Boolean vIf = false;

    @ToRenderAttrs(toModelValue = true)
    private List<String> urlList = new ArrayList<>();

    @ToRenderAttrs(isEval = true)
    private String onClose;

    @Override
    public void beforeRender() {
        setComponent("ElImageViewer");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
