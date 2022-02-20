package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.entity.vo.TitleValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.TinymceTemplate;
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
public class Tinymce extends AssemblyAbstract {

    @Setter(AccessLevel.NONE)
    private List<String> plugins = new ArrayList<String>() {
        {
            add("print");
            add("preview");
            add("searchreplace");
            add("autolink");
            add("directionality");
            add("visualblocks");
            add("visualchars");
            add("fullscreen");
            add("link");
            add("media");
            add("image");
            add("imagetools");
            add("template");
//            add("advcode");
//            add("codesample");
            add("table");
            add("charmap");
            add("hr");
            add("pagebreak");
            add("nonbreaking");
            add("anchor");
            add("insertdatetime");
            add("advlist");
            add("lists");
            add("wordcount");
            add("textpattern");
//            add("help");
            add("emoticons");
            add("autosave");
            add("bdmap");
            add("indent2em");
            add("autoresize");
            add("formatpainter");
            add("axupimgs");
            add("importword");
            add("kityformula-editor");
        }
    };

    @Setter(AccessLevel.NONE)
    private List<String> toolbar = new ArrayList<String>() {
        {
            add("code");add("undo");add("redo");add("restoredraft");add("|");
            add("cut");add("copy");add("paste");add("pastetext");add("|");
            add("forecolor");add("backcolor");add("bold");add("italic");add("underline");add("strikethrough");add("link");add("anchor");add("|");
            add("alignleft");add("aligncenter");add("alignright");add("alignjustify");add("outdent");add("indent");add("indent2em");add("hr");add("|");
            add("styleselect");add("fontselect");add("fontsizeselect");add("lineheight");add("formatpainter");add("|");
            add("bullist");add("numlist");add("pagebreak");add("insertdatetime");add("|");add("blockquote");add("subscript");add("superscript");add("removeformat");add("|");
            add("table");add("image");add("axupimgs");add("media");add("emoticons");add("bdmap");add("importword");add("kityformula-editor");
        }
    };

    // 字体大小分类
    @ComponentAttr
    private String fontsize = "12px 14px 16px 18px 24px 36px 48px 56px 72px";

    // 超链接列表
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<TitleValue> linkList;

    // 图片列表
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<TitleValue> imageList;

    // 模板列表
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<TinymceTemplate> templateList;

    // 替换参数列表
    @ComponentAttr(prevStr = ":", isRef = true)
    private Dict tplReplaceValues;

    // 高度
    @ComponentAttr(prevStr = ":")
    private Integer height;

    // 高度
    @ComponentAttr(prevStr = ":")
    private Integer width;

    // 上传路径
    @ComponentAttr
    private String uploadUrl = "/thinker/admin/upload";

    public Tinymce(String prop, String label) {
        super(prop, label);
    }

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        PageParams.setImport("@/components/tinymce/index.vue", "tinymce", false);
        PageParams.setComponents("tinymce");

        // 设置plugins
        String pluginStr = "[]";
        if(Validator.isNotEmpty(plugins)) {
            pluginStr = getLayoutId()+"_"+getProp()+"_plugins";
            PageParams.createRef(pluginStr, JSON.toJSONString(plugins));
        }

        // 设置toolbar
        String toolbarStr = "[]";
        if(Validator.isNotEmpty(toolbar)) {
            toolbarStr = getLayoutId()+"_"+getProp()+"_toolbar";
            PageParams.createRef(toolbarStr, "[\""+ StrUtil.join(" ", toolbar) +"\"]");
        }

        return "<tinymce v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+PageParams.strComponentAttrs(this)+" " +
                ":plugins=\""+pluginStr+"\" :toolbar=\""+toolbarStr+"\" />";
    }
}
