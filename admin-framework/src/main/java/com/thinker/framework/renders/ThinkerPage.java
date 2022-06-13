package com.thinker.framework.renders;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.AccessLevel;
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
public class ThinkerPage {

    public ThinkerPage() {}

    public ThinkerPage(RunClosure runClosure) { if(runClosure != null) runClosure.run(this); }

    //使用闭包的形式
    public interface RunClosure {
        void run(ThinkerPage thinkerPage);
    }

    private String id = DefineComponent.getRenderId();

    // 默认的渲染方法
    private RenderResult render = RenderResult.create("div", Dict.create().set("style", "padding: 10px;background-color: white;height: calc(100% - 20px);border-radius: 4px;").set("id", id));

    // 设置
    @Setter(AccessLevel.NONE)
    private List<RootRender> children = new ArrayList<>();

    // 最后统一调用render
    public ThinkerResponse render() {
        String pageMethodName = DefineComponent.getRenderId();

        if(Validator.isEmpty(render.getAttrs().get("id"))){
            // 如果没有id，就补充一下
            render.getAttrs().set("id", pageMethodName);
        }

        // 最后渲染json
        render.getChildren().addAll(children.stream().map(rootRender -> {
            if(!rootRender.getId().startsWith(pageMethodName)) {
                rootRender.setId(render.getAttrs().getStr("id") + "_" + rootRender.getId());
            }
            return rootRender.render();
        }).collect(Collectors.toList()));

        return new ThinkerResponse().data(
                Dict.create().set("modelValue", DefineComponent.getModelValue())
                        .set("formValue", DefineComponent.getFormValue())
                        .set("render", render)
                        .set("renderReady", StrUtil.join("", DefineComponent.getRenderReady()))
                        .set("renderDataReady", StrUtil.join("", DefineComponent.getRenderDataReady()))
                        .set("getDataBefore", StrUtil.join("", DefineComponent.getGetDataBefore()))
                        .set("components", DefineComponent.getImportComponent())
        ).success();
    }
}
