package com.thinker.framework.renders.assemblys.form.plugins;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.ElSizeEnum;
import com.thinker.framework.renders.entity.enums.InputTypeEnum;
import com.thinker.framework.renders.entity.form.ElInputAutoSize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ElInput extends FormPluginRender<ElInput> {

    @ToRenderAttrs
    private InputTypeEnum type;

    @ToRenderAttrs
    private Integer maxlength;

    @ToRenderAttrs
    private Integer minlength;

    @ToRenderAttrs
    private Boolean showWordLimit;

    @ToRenderAttrs
    private String placeholder;

    @ToRenderAttrs
    private Boolean clearable;

    // specifies the format of the value presented input.(only works when type is 'input')
    @ToRenderAttrs(isEval = true)
    private String formatter;

    // specifies the format of the value presented input.(only works when type is 'input')
    @ToRenderAttrs(isEval = true)
    private String parser;

    @ToRenderAttrs
    private Boolean showPassword;

    @ToRenderAttrs
    private Boolean disabled;

    @ToRenderAttrs
    private ElSizeEnum size;

    @ToRenderAttrs
    private String prefixIcon;

    @ToRenderAttrs
    private String suffixIcon;

    @ToRenderAttrs
    private Integer rows;

    @ToRenderAttrs
    private ElInputAutoSize autosize;

    @ToRenderAttrs
    private Boolean readonly;

    @ToRenderAttrs
    private Integer max;

    @ToRenderAttrs
    private Integer min;

    @ToRenderAttrs
    private Float step;

    @ToRenderAttrs
    private Boolean resize;

    @ToRenderAttrs
    private Boolean autofocus;

    @ToRenderAttrs
    private Dict inputStyle;

    // 设置变更时执行

    @ToRenderAttrs(isEval = true)
    private String onBlur;

    @ToRenderAttrs(isEval = true)
    private String onFocus;

    @ToRenderAttrs(isEval = true)
    private String onChange;

    @ToRenderAttrs(isEval = true)
    private String onInput;

    @ToRenderAttrs(isEval = true)
    private String onClear;

    // 渲染模板
    @ToRenderAttrs(isSlot = true)
    private String prefix;

    // 渲染模板
    @ToRenderAttrs(isSlot = true)
    private String prepend;

    // 渲染模板
    @ToRenderAttrs(isSlot = true)
    private String suffix;

    // 渲染模板
    @ToRenderAttrs(isSlot = true)
    private String append;

    @Override
    public void beforeRender() {
        setComponent("ElInput");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
