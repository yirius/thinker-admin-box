package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.ThinkerHtml;
import com.thinker.framework.framework.renders.bo.TableLayerDataCase;
import com.thinker.framework.framework.renders.bo.XlsxLayerDataCase;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.renders.page.plugins.ThinkerPopConfirm;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Button extends AssemblyAbstract {

    public interface Closure {
        void run(Button button);
    }

    @ComponentAttr
    private LayoutSize size = LayoutSize.NORMAL;

    @ComponentAttr
    private ButtonType type = ButtonType.NORMAL;

    @ComponentAttr(prevStr = ":")
    private Boolean plain;

    @ComponentAttr(prevStr = ":")
    private Boolean round;

    @ComponentAttr(prevStr = ":")
    private Boolean circle;

    @ComponentAttr(prevStr = ":")
    private String icon;

    @ComponentAttr(prevStr = ":")
    private Boolean autofocus;

    @ComponentAttr(prevStr = ":")
    private Boolean autoInsertSpace;

    public Button(String prop, String label) {
        super(prop, label);
    }

    // 点击无需
    private String click;
    // 点击的参数
    private String clickArgs;

    private String getClickStr() {
        if(Validator.isNotEmpty(click)) {
            PageParams.setMethods(getLayoutId() + "_" + getProp() + "_click", "("+(Validator.isNotEmpty(clickArgs)?clickArgs:"")+") {" +
                    "   " + click +
                    "}");

            return "@click=\"" + getLayoutId() + "_" +getProp() + "_click"+(Validator.isNotEmpty(clickArgs)?"("+clickArgs+")":"")+"\"";
        }

        return "";
    }

    public Button columnButtonClick(String click) {
        this.click = click;
        this.clickArgs = "scope";
        return this;
    }

    /**
     * 设置popConfirm
     */
    @Setter(AccessLevel.NONE)
    private ThinkerPopConfirm popConfirm;

    public ThinkerPopConfirm openPopConfirm() {
        if(popConfirm == null) {
            popConfirm = initLayoutAbstract(new ThinkerPopConfirm());
        }
        return popConfirm;
    }

    // 弹出的界面相关设置，需要有默认参数
    private XlsxLayerDataCase layerXlsx = new XlsxLayerDataCase();

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private boolean isXlsx = false;

    // 轮询信息
    private String eachData = null;

    /**
     * 设置xlsx打开
     * @return
     */
    public Button xlsx(String eachData) {
        PageParams.setImport("@/components/xlsx/read.vue", "ReadXlsx", false);
        PageParams.setComponents("ReadXlsx");
        this.isXlsx = true;
        this.eachData = eachData;

        return this;
    }

    @Override
    public String renderContent() {
        if(Validator.isNotEmpty(icon)) {
            PageParams.setImport("@element-plus/icons", icon);
            PageParams.setSetupReturn(icon, icon);
        }

        if(this.isXlsx) {
            // 设置打开参数
            PageParams.createReactive(getLayoutId() + "_xlsx_layer_data", JSON.toJSONString(layerXlsx));
            PageParams.setTplLayout(new ThinkerHtml().setHtml("<ReadXlsx :layer=\"" + getLayoutId() + "_xlsx_layer_data\"></ReadXlsx>"));

            // 设置点击按钮的打开
            this.setClick("this."+getLayoutId()+"_xlsx_layer_data.show = true;\n" +
                    "this."+getLayoutId()+"_xlsx_layer_data.eachData = (data) => {'"+this.eachData+"'};\n" + this.click);
        }

        return "<el-button "+getAttrsStr()+" "+ PageParams.strComponentAttrs(this) +" "+getClickStr()+">"+getLabel()+"</el-button>";
    }

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        if(popConfirm != null) {
            if(popConfirm.getLayoutId().equals(getLayoutId())) {
                popConfirm.setLayoutId(getLayoutId() + "_" + getProp());
            }
            return popConfirm.setContent(renderContent()).render();
        }

        return renderContent();
    }
}
