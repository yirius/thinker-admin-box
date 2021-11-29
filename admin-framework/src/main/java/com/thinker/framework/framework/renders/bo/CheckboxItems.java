package com.thinker.framework.framework.renders.bo;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CheckboxItems {

    public static CheckboxItems create(String label, Object value) {
        return new CheckboxItems().setLabel(label).setValue(value);
    }

    private String label;
    private Object value;
    private boolean disabled = false;
    private boolean border = false;
    private LayoutSize size = LayoutSize.SMALL;
    private boolean checked = false;
    private boolean indeterminate = false;
}