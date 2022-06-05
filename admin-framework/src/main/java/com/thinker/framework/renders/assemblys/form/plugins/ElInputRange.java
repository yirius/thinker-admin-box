package com.thinker.framework.renders.assemblys.form.plugins;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElInputRange extends FormPluginRender<ElInputRange> {

    private RootRender startRender = new ElInput();

    private RootRender endRender = new ElInput();

    @ToRenderAttrs
    private String rangeKey = "-";

    /**
     * 直接运行，防止多余代码
     * @param elInputRangeRunClosure
     * @return
     */
    public ElInputRange runClosure(RunClosure<ElInputRange> elInputRangeRunClosure) {
        elInputRangeRunClosure.run(this);
        return this;
    }

    @Override
    public void beforeRender() {
        setComponent("ThinkerInputRange");

        attrs.set("startRender", startRender.render());
        attrs.set("endRender", endRender.render());

        setIsModelValueArray(true);
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
