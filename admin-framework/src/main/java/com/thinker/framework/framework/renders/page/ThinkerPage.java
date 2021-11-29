package com.thinker.framework.framework.renders.page;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ThinkerPage extends LayoutAbstract {

    public ThinkerPage() {}

    public ThinkerPage(Closure closure) {
        if(closure != null) closure.run(this);
    }

    //使用闭包的形式
    public interface Closure {
        void run(ThinkerPage thinkerPage);
    }

    // 内部存储的直接使用模板
    private List<LayoutAbstract> layouts = new ArrayList<>();

    // 是否使用<div class="layout-container">包裹
    private boolean isLayoutContainer = true;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        StrBuilder tplStr = new StrBuilder();
        // 判断是否包裹
        if(isLayoutContainer) tplStr.append("<div class=\"layout-container\">\n");
        // 渲染挂载在PAGE内部的
        layouts.forEach(layoutAbstract -> tplStr.append(layoutAbstract.render()));
        // 渲染其他组件的全局模块
        PageParams.getTplLayout().forEach(layoutAbstract -> tplStr.append(layoutAbstract.render()));
        // 判断是否包裹
        if(isLayoutContainer) tplStr.append("</div>\n");

        // 补充默认导入的模块
        PageParams.setImport("vue", "defineComponent", "ref", "unref", "reactive", "toRaw");
        PageParams.setImport("element-plus", "ElMessage", "ElMessageBox");
        // 渲染导入的模块
        StrBuilder importStr = new StrBuilder();
        PageParams.getImport().forEach((s, importCase) -> importStr.append(importCase).append("\n"));

        // setup里返回的参数
        StrBuilder setupReturnStr = new StrBuilder();
        PageParams.getSetupReturn().forEach((s, s2) -> {
            if(s.equals(s2)) {
                setupReturnStr.append(s).append(",\n");
            } else {
                setupReturnStr.append(s).append(":").append(s2).append(",\n");
            }
        });

        // props返回的参数
        StrBuilder propsStr = new StrBuilder();
        PageParams.getProps().forEach((s, s2) -> propsStr.append(s).append(":").append(s2).append(","));
        // method返回的参数
        StrBuilder methodsStr = new StrBuilder();
        PageParams.getMethods().forEach((s, s2) -> methodsStr.append(s).append(s2).append(","));
        // computed返回的参数
        StrBuilder computedStr = new StrBuilder();
        PageParams.getComputed().forEach((s, s2) -> computedStr.append(s).append(s2).append(","));
        // computed返回的参数
        StrBuilder watchStr = new StrBuilder();
        PageParams.getWatch().forEach((s, s2) -> watchStr.append(s).append(s2).append(","));

        // 组装以下空白参数，可以自由撰写
        StrBuilder optionsStr = new StrBuilder();
        if(!propsStr.isEmpty()) {
            optionsStr.append("props: {\n").append(propsStr.toStringAndReset()).append("\n},");
        }
        if(!computedStr.isEmpty()) {
            optionsStr.append("computed: {\n").append(computedStr.toStringAndReset()).append("\n},");
        }
        if(!watchStr.isEmpty()) {
            optionsStr.append("watch: {\n").append(watchStr.toStringAndReset()).append("\n},");
        }
        if(!methodsStr.isEmpty()) {
            optionsStr.append("methods: {\n").append(methodsStr.toStringAndReset()).append("\n},");
        }

        return thinkerrender.adminbox.page.page.template(
                tplStr.toStringAndReset() + "\n",
                importStr.toStringAndReset(),
                StrUtil.join(",", PageParams.getComponents()),
                StrUtil.join("\n", PageParams.getSetupScript()) + StrUtil.join("\n", PageParams.getSetupSuffixScript()),
                setupReturnStr.toStringAndReset(),
                optionsStr.toStringAndReset(), "", ""
        ).render().toString();
    }

    @Override
    public String toString() {
        return String.valueOf(render());
    }
}
