package com.thinker.framework.renders.assemblys.page;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerTags;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ElIcon extends RootRender {

    @ToRenderAttrs
    private String color;

    @ToRenderAttrs
    private Integer size;

    private String iconName;

    @Override
    public void beforeRender() {
        setComponent("ElIcon");
        if(Validator.isNotEmpty(iconName)) {
            getChildren().add(new ThinkerTags().runClosure(tags -> {
                tags.setComponent(iconName);
            }));
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
