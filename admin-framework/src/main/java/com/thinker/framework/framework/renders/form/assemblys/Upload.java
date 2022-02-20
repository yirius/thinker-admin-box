package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Upload extends AssemblyAbstract {

    @ComponentAttr
    private String action = "/thinker/admin/upload";

    @ComponentAttr(prevStr = ":", isRef = true)
    private Dict headers;

    @ComponentAttr
    private String method = "POST";

    @ComponentAttr(prevStr = ":")
    private Boolean multiple;

    @ComponentAttr(prevStr = ":", isRef = true)
    private Dict data;

    @ComponentAttr
    private String name;

    @ComponentAttr(prevStr = ":")
    private Boolean withCredentials;

    @ComponentAttr(prevStr = ":")
    private Boolean showFileList;

    @ComponentAttr(prevStr = ":")
    private Boolean drag;

    @ComponentAttr
    private String accept;

    @ComponentAttr(prevStr = ":")
    private Boolean thumbnailMode;

    @ComponentAttr(prevStr = ":", isRef = true)
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
    }

    @ComponentAttr
    private ListType listType;

    @ComponentAttr(prevStr = ":")
    private Boolean autoUpload;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "options")
    private String httpRequest;

    @ComponentAttr(prevStr = ":")
    private Integer limit;

    public Upload(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "file")
    private String onPreview;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "file,fileList")
    private String onRemove;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "response,file,fileList")
    private String onSuccess;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "err,file,fileList")
    private String onError;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "event,file,fileList")
    private String onProgress;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "file,fileList")
    private String onChange;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "file")
    private String beforeUpload;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "file,fileList")
    private String beforeRemove;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "files,fileList")
    private String onExceed;

    private String defaultSlot;

    public String getDefaultSlotStr() {
        if(Validator.isNotEmpty(defaultSlot)) {
            return slotStr("default", "", defaultSlot);
        }
        return "";
    }

    private String triggerSlot;

    public String getTriggerSlotStr() {
        if(Validator.isNotEmpty(triggerSlot)) {
            return slotStr("trigger", "", triggerSlot);
        }
        return "";
    }

    private String tipSlot;

    public String getTipSlotStr() {
        if(Validator.isNotEmpty(triggerSlot)) {
            return slotStr("tip", "", tipSlot);
        }
        return "";
    }

    @Setter(AccessLevel.NONE)
    private Button clickButton = initLayoutAbstract(new Button("uploadBtn", "点击上传")).setType(ButtonType.PRIMARY);

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        if(Validator.isEmpty(getDefaultValue())) {
            setDefaultValue(new JSONArray());
        }

        // 触发修改change
        this.onChange = "fileList.forEach(map => {" +
                "   if(map.status=='success') {" +
                "       let url = map.response?map.response.data."+(Validator.isNotEmpty(name)?name:"file") + ":map.url;" +
                "       if(this." + getLayoutId() + "_formValue." + getProp() + ".indexOf(url) < 0) {" +
                "           this." + getLayoutId() + "_formValue." + getProp() + ".push(url);" +
                "       }" +
                "   }" +
                "});" + this.onChange;

        // 触发删除
        this.onRemove = "let url = file.response?file.response.data."+(Validator.isNotEmpty(name)?name:"file") + ":file.url;" +
                "this." + getLayoutId() + "_formValue." + getProp() + ".splice(" +
                "   this." + getLayoutId() + "_formValue." + getProp() + ".indexOf(url), 1" +
                ");"+ this.onRemove;

        // 设置数据读取后的渲染
        PageParams.setParseSetupData("let _"+getLayoutId()+"_"+getProp()+"_uploadData = " + getLayoutId() + "_formValue.value."+getProp()+";" +
                "if(typeof _"+getLayoutId()+"_"+getProp()+"_uploadData != 'undefined' && _"+getLayoutId()+"_"+getProp()+"_uploadData != null) {" +
                "   _"+getLayoutId()+"_"+getProp()+"_uploadData.forEach(item => {" +
                "       let allNames = item.split('/');" +
                "       " + getLayoutId() + "_" + getProp() + "_fileList.value.push({name: allNames[allNames.length-1], url: item});" +
                "   });" +
                "}");

        // 复写一下提交逻辑
        PageParams.setImport("@/components/upload/index", "uploadHttpRequestApi");
        if(Validator.isEmpty(this.httpRequest)) {
            this.httpRequest = "return uploadHttpRequestApi(options)";
        }

        // 写一下图片的预览逻辑
        PageParams.createRef(
                getLayoutId() + "_" + getProp() + "_imageViewer",
                JSON.toJSONString(Dict.create().set("urlList", new ArrayList<>()).set("show", false))
        );
        if(Validator.isEmpty(this.onPreview)) {
            this.onPreview = "let url = file.response?file.response.data."+(Validator.isNotEmpty(name)?name:"file") + ":file.url;" +
                    "this." + getLayoutId() + "_" + getProp() + "_imageViewer.urlList = [url];" +
                    "this." + getLayoutId() + "_" + getProp() + "_imageViewer.show = true;";
        }
        // 写一下关闭
        PageParams.setMethods(getLayoutId() + "_" + getProp() + "_imageViewer_onClose", "() {" +
                "   this." + getLayoutId() + "_" + getProp() + "_imageViewer.show = false;" +
                "}");

        return "<el-upload v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this)+">\n" +
                "" + clickButton.render() + getDefaultSlotStr() + getTriggerSlotStr() + getTipSlotStr() +
                "</el-upload>" +
                "<el-image-viewer v-if=\""+getLayoutId() + "_" + getProp() + "_imageViewer.show\" " +
                " :onClose=\""+getLayoutId() + "_" + getProp()+"_imageViewer_onClose\" " +
                " :url-list=\""+getLayoutId() + "_" + getProp() + "_imageViewer.urlList\" />";
    }
}
