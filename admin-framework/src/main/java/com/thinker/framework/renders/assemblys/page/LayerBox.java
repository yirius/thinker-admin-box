package com.thinker.framework.renders.assemblys.page;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerTags;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.page.LayerBoxProps;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class LayerBox extends RootRender {

    @ToRenderAttrs(isJsonObject = true)
    private LayerBoxProps layer = new LayerBoxProps();

    /**
     * 添加一个json解析界面
     * @param id
     * @param url
     * @return
     */
    public LayerBox addJsonPage(String id, String url) {
        this.setId(id);
        this.getChildren().add(new ThinkerTags().runClosure(rootRender -> {
            rootRender.setComponent("ViewBaseComponent").setId("jsonpage");
            rootRender.getAttrs().set("renderUrl", url);
        }));
        return this;
    }

    private Integer formIndex;

    /**
     * 添加form，可以直接显示
     * @param id
     * @param renderUrl
     * @param formIndex
     * @return
     */
    public LayerBox addFormPage(String id, String renderUrl, Integer formIndex) {
        this.formIndex = formIndex;
        return addJsonPage(id, renderUrl);
    }

    @Override
    public void beforeRender() {
        setComponent("layerBox");

        if(formIndex != null) {
            setOnEvent("confirm", "(e) => {" +
                    "   $refs."+getId()+"_"+getChildren().get(0).getId()+".findRefs('thinkerform')["+formIndex+"].$emit('submit', $refs, \""+getId()+"\");" +
                    "}");
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
