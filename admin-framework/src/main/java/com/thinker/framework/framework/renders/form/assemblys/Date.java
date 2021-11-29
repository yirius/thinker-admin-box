package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONArray;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.LayoutLargeSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Date extends AssemblyAbstract {

    @ComponentAttr
    private LayoutLargeSize size;

    /**
     * 文本框可输入
     */
    @ComponentAttr(prevStr = ":")
    private Boolean editable;

    /**
     * 	是否显示清除按钮
     */
    @ComponentAttr(prevStr = ":")
    private Boolean clearable;

    @ComponentAttr
    private String placeholder;

    @ComponentAttr
    private String startPlaceholder;

    @ComponentAttr
    private String endPlaceholder;

    public enum DateType {
        YEAR("year"), MONTH("month"), DATE("date"),
        DATES("dates"), DATETIME("datetime"), WEEK("week"),
        DATETIMERANGE("datetimerange"), DATERANGE("daterange"), MONTHRANGE("monthrange");

        private final String type;
        DateType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    @ComponentAttr
    private DateType type;

    /**
     * 显示在输入框中的格式
     */
    @ComponentAttr
    private String format;

    /**
     * DatePicker 下拉框的类名
     */
    @ComponentAttr
    private String popperClass;

    /**
     * 选择范围时的分隔符
     */
    @ComponentAttr
    private String rangeSeparator;

    /**
     * 可选，绑定值的格式。 不指定则绑定值为 Date 对象
     */
    @ComponentAttr
    private String valueFormat;

    /**
     * 在范围选择器里取消两个日期面板之间的联动
     */
    @ComponentAttr(prevStr = ":")
    private Boolean unlinkPanels;

    /**
     * 自定义前缀图标
     */
    @ComponentAttr
    private String prefixIcon;

    /**
     * 清楚日期图标
     */
    @ComponentAttr
    private String clearIcon;

    /**
     * 一个用来判断该日期是否被禁用的函数，接受一个 Date 对象作为参数 ，应该返回一个 Boolean 值 Should return a Boolean
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "dateVal")
    private String disabledDate;

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<TextValue> shortcuts;

    public Date(String prop, String label) {
        super(prop, label);
    }

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "obj")
    private String blur;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "obj")
    private String focus;

    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "dateVal")
    private String calendarChange;

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        if(Validator.isEmpty(valueFormat)) {
            if(type != null && type.toString().contains("time")) {
                valueFormat = "YYYY-MM-DD HH:mm:ss";
            } else {
                valueFormat = "YYYY-MM-DD";
            }
        }

        return "<el-date-picker v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this, Arrays.asList("prop", "label")) +" >" +
                "</el-date-picker>";
    }
}
