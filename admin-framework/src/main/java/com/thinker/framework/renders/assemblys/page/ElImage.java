package com.thinker.framework.renders.assemblys.page;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.slots.ElImageErrorSlot;
import com.thinker.framework.renders.assemblys.slots.ElImageSlots;
import com.thinker.framework.renders.assemblys.table.VxeColumnSlots;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ElImage extends RootRender {
    @ToRenderAttrs
    private String src;

    @ToRenderAttrs
    private String fit;

    @ToRenderAttrs
    private Boolean hideOnClickModal;

    @ToRenderAttrs
    private Boolean lazy;

    @ToRenderAttrs
    private String scrollContainer;

    @ToRenderAttrs
    private String alt;

    @ToRenderAttrs
    private String referrerPolicy;

    @ToRenderAttrs
    private String previewSrcList;

    @ToRenderAttrs
    private Integer zIndex;

    @ToRenderAttrs
    private Integer initialIndex;

    @ToRenderAttrs
    private Boolean closeOnPressEscape;

    @ToRenderAttrs
    private Boolean previewTeleported;

    // 错误slot
    private List<RootRender> errorTpl = new ArrayList<>();

    public ElImage columnShow(String imgName) {
        attrs.set("style", "width: 50px;height: 50px;margin-top: 5px;");
        setSrc("[`eval`](function() { " +
                "   var thumbValue = slotData.row." + imgName + ";" +
                "   if(thumbValue) {" +
                "       if(_AdminIs.isArray(thumbValue)) {" +
                "           return thumbValue[0];" +
                "       } else if(_AdminIs.isString(thumbValue)) {" +
                "           if(thumbValue.indexOf(',') >= 0) {" +
                "               return thumbValue.split(',')[0];" +
                "           } else if(thumbValue.indexOf('[') >= 0) {" +
                "               return JSON.parse(thumbValue)[0];" +
                "           }" +
                "       }" +
                "   }" +
                "   return thumbValue;" +
                " })()");
        setPreviewSrcList("[`eval`](function() { " +
                "   var thumbValue = slotData.row." + imgName + ";" +
                "   if(thumbValue) {" +
                "       if(_AdminIs.isArray(thumbValue)) {" +
                "           return thumbValue;" +
                "       } else if(_AdminIs.isString(thumbValue)) {" +
                "           if(thumbValue.indexOf(',') >= 0) {" +
                "               return thumbValue.split(',');" +
                "           } else if(thumbValue.indexOf('[') >= 0) {" +
                "               return JSON.parse(thumbValue);" +
                "           }" +
                "       }" +
                "   }" +
                "   return [thumbValue];" +
                " })()");
        return this;
    }

    @Override
    public void beforeRender() {
        setComponent("ElImage");

        if(!getSlots().containsKey("error")) {
            if(this.errorTpl.size() == 0) {
                errorTpl.add(new ElImageErrorSlot());
            }
            getSlots().put("error", errorTpl);
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
