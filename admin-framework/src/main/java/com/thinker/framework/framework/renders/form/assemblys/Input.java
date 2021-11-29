package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.InputAutoSize;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class Input extends AssemblyAbstract {

    public enum InputType {
        TEXT("text"), TEXTAREA("textarea"), NUMBER("number"), COLOR("color"),
        DATE("date"), EMAIL("email"), FILE("file"), HIDDEN("hidden"), MONTH("month"),
        PASSWORD("password"), RANGE("range"), TEL("tel"), TIME("time"), URL("url"), WEEK("week");

        private final String type;
        InputType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    @ComponentAttr
    private InputType type;

    @ComponentAttr(prevStr = ":")
    private Integer maxlength;

    @ComponentAttr(prevStr = ":")
    private Integer minlength;

    @ComponentAttr(prevStr = ":")
    private Boolean showWordLimit;

    @ComponentAttr
    private String placeholder;

    @ComponentAttr(prevStr = ":")
    private Boolean clearable;

    @ComponentAttr(prevStr = ":")
    private Boolean showPassword;

    @ComponentAttr
    private LayoutSize size;

    @ComponentAttr
    private String prefixIcon;

    @ComponentAttr
    private String suffixIcon;

    @ComponentAttr(prevStr = ":")
    private Integer rows;

    @ComponentAttr(prevStr = ":")
    private InputAutoSize autosize;

    @ComponentAttr(prevStr = ":")
    private Integer max;

    @ComponentAttr(prevStr = ":")
    private Integer min;

    @ComponentAttr(prevStr = ":")
    private Integer step;

    @ComponentAttr(prevStr = ":")
    private Boolean resize;

    @ComponentAttr(prevStr = ":")
    private Boolean autofocus;

    @ComponentAttr(prevStr = ":")
    private Dict inputStyle;

    public Input(String prop, String label) {
        super(prop, label);
    }

    /**
     * 	输入框头部内容插槽
     */
    private String prefix;

    public String getPrefixSlot() {
        if(this.type == null || this.type.equals(InputType.TEXT)) {
            if(Validator.isNotEmpty(prefix)) {
                return slotStr("prefix", "", prefix);
            }
        }
        return "";
    }

    /**
     * 	输入框头部尾部插槽
     */
    private String suffix;

    public String getSuffixSlot() {
        if(this.type == null || this.type.equals(InputType.TEXT)) {
            if(Validator.isNotEmpty(suffix)) {
                return slotStr("suffix", "", suffix);
            }
        }
        return "";
    }

    /**
     * 	输入框头部前置插槽
     */
    private String prepend;

    public String getPrependSlot() {
        if(this.type == null || this.type.equals(InputType.TEXT)) {
            if(Validator.isNotEmpty(prepend)) {
                return slotStr("prepend", "", prepend);
            }
        }
        return "";
    }

    /**
     * 	输入框头部后置插槽
     */
    private String append;

    public String getAppendSlot() {
        if(this.type == null || this.type.equals(InputType.TEXT)) {
            if(Validator.isNotEmpty(append)) {
                return slotStr("append", "", append);
            }
        }
        return "";
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String blur;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "e")
    private String focus;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String input;

    @ComponentAttr(prevStr = "@", isEvent = true)
    private String clear;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public String renderContent() {
        return "<el-input v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this) +">" +
                getPrependSlot() + getPrefixSlot() + getSuffixSlot() + getAppendSlot() +
                "</el-input>";
    }
}
