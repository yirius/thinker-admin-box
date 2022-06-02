package com.thinker.framework.renders.assemblys;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerTags extends RootRender {

    public ThinkerTags runClosure(RunClosure<ThinkerTags> runClosure) {
        runClosure.run(this);
        return this;
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
