package com.thinker.framework.renders.assemblys.form.plugins;

import cn.hutool.core.lang.Dict;
import com.fasterxml.jackson.annotation.JsonValue;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.form.plugins.tinymce.ToolbarTypes;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class Tinymce extends FormPluginRender<Tinymce> {

    @ToRenderAttrs
    private List<String> toolbar = null;

    @ToRenderAttrs
    private List<String> plugins = null;

    @ToRenderAttrs
    private String fontsize;

    @ToRenderAttrs
    private String height;

    @ToRenderAttrs
    private String width;

    @ToRenderAttrs
    private String uploadUrl;

    @ToRenderAttrs
    private List<String> linkList;

    @ToRenderAttrs
    private List<String> imageList;

    @ToRenderAttrs
    private List<String> templateList;

    @ToRenderAttrs
    private Dict tplReplaceValues;

    @Override
    public void beforeRender() {
        setComponent("Tinymce");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }

}
