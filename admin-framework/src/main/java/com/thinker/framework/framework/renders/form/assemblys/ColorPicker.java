package com.thinker.framework.framework.renders.form.assemblys;

import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ColorPicker extends AssemblyAbstract {

    @ComponentAttr
    private LayoutSize size = LayoutSize.NORMAL;

    /**
     * 	是否支持透明度选择
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showAlpha;

    public enum ColorFormat {
        HSL("hsl"), HSV("hsv"), HEX("hex"), RGB("rgb");

        String colorFormat;
        ColorFormat(String colorFormat) {
            this.colorFormat = colorFormat;
        }

        @Override
        public String toString() {
            return colorFormat;
        }
    }

    /**
     * 写入 v-model 的颜色的格式
     */
    @ComponentAttr
    private ColorFormat colorFormat;

    /**
     * ColorPicker 下拉菜单自定义图标组件
     */
    @ComponentAttr
    private String popperClass;

    /**
     * 预定义颜色
     */
    @ComponentAttr(prevStr = ":")
    private List<String> predefine;

    public ColorPicker(String prop, String label) {
        super(prop, label);
    }

    /**
     * 当绑定值变化时触发
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String change;

    /**
     * 	面板中当前显示的颜色发生改变时触发
     */
    @ComponentAttr(prevStr = "@", isEvent = true, eventArgs = "value")
    private String activeChange;

    /**
     * 由于组件都同质化，重写一下渲染函数
     *
     * @return
     */
    @Override
    public String renderContent() {
        return "<el-color-picker v-model=\""+getLayoutId()+"_formValue."+getProp()+"\" "+ PageParams.strComponentAttrs(this, Arrays.asList("prop", "label")) +" />";
    }
}
