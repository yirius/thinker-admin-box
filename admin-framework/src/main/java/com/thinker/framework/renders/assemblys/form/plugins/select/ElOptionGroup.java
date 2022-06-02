package com.thinker.framework.renders.assemblys.form.plugins.select;

import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.abstracts.RootRender;
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
public class ElOptionGroup extends RootRender {

    // 设置参数
    private List<LabelValue> options;

    @Override
    public void beforeRender() {
        setComponent("ElOptionGroup");

        if(options != null) {
            setChildren(options.stream().map(labelValue -> new ElOption().setAttrs(labelValue)).collect(Collectors.toList()));
        }
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
