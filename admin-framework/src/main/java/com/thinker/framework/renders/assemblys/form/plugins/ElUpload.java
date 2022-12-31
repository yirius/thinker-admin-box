package com.thinker.framework.renders.assemblys.form.plugins;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerTags;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElUpload extends FormPluginRender<ElUpload> {

    @ToRenderAttrs
    private String action = "/thinker/admin/upload";

    @ToRenderAttrs
    private Dict headers;

    @ToRenderAttrs
    private String method = "POST";

    @ToRenderAttrs
    private Boolean multiple;

    @ToRenderAttrs
    private Dict data;

    @ToRenderAttrs
    private String name;

    @ToRenderAttrs
    private Boolean withCredentials;

    @ToRenderAttrs
    private Boolean showFileList;

    @ToRenderAttrs
    private Boolean drag;

    @ToRenderAttrs
    private String accept;

    @ToRenderAttrs
    private Boolean thumbnailMode;

    @ToRenderAttrs(isFormValue = true, isFormValueField = true)
    private List<Dict> fileList = new ArrayList<>();

    public enum ListType {
        TEXT("text"), PICTURE("picture"), PICTURECARD("picture-card");

        private final String size;
        ListType(String size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return size;
        }

        @JsonValue
        public String getSize() {
            return size;
        }
    }

    @ToRenderAttrs
    private ListType listType;

    @ToRenderAttrs
    private Boolean autoUpload;

    @ToRenderAttrs(isEval = true)
    private String httpRequest;

    @ToRenderAttrs
    private Integer limit;

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs(isEval = true)
    private String onPreview;

    @ToRenderAttrs(isEval = true)
    private String onRemove;

    @ToRenderAttrs(isEval = true)
    private String onSuccess;

    @ToRenderAttrs(isEval = true)
    private String onError;

    @ToRenderAttrs(isEval = true)
    private String onProgress;

    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String beforeUpload;

    @ToRenderAttrs(isEval = true)
    private String beforeRemove;

    @ToRenderAttrs(isEval = true)
    private String onExceed;

    @Override
    public void beforeRender() {
        setComponent("ElUpload");

        if(listType == null || listType.toString().equals("text")) {

        } else {
            this.onPreview = "(file) => { " +
                    "var fileList = props.modelRefsValue['"+getModelValue()+"'].value, showFileList = [];" +
                    "if(fileList) {" +
                    "   fileList.forEach(item => {" +
                    "       showFileList.push(item.response?item.response.data."+(Validator.isNotEmpty(name)?name:"file") + ":item.url);" +
                    "   })" +
                    "} else {" +
                    "   showFileList.push(file.response?file.response.data."+(Validator.isNotEmpty(name)?name:"file") + ":file.url);" +
                    "}" +
                    "props.modelRefsValue['"+getModelValue()+"_urlList'].value = showFileList;" +
                    "props.modelRefsValue['"+getModelValue()+"_vIf'].value = true; " +
                    "}";
        }

        if(Validator.isEmpty(onChange)) {
            this.onChange = "(file, files) => {" +
                    "   props.modelRefsValue['"+getModelValue()+"'].value = files;" +
                    "}";
        }

        if(getChildren().size() == 0) {
            if(listType != null && listType.toString().equals("picture-card")) {
                getChildren().add((new ThinkerTags()).runClosure(rootRender -> {
                    rootRender.setComponent("ElIcon");
                    rootRender.getChildren().add(new ThinkerTags().setComponent("Plus"));
                }));
            } else {
                getChildren().add(new ElButton().setType("primary").setLabel("点击上传"));
            }
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
