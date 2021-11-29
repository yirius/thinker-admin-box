package com.thinker.framework.framework.renders.form.assemblys;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.abstracts.renders.AssemblyAbstract;
import com.thinker.framework.framework.renders.PageParams;
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

    @Override
    public String renderContent() {
        if(Validator.isNotEmpty(icon)) {
            PageParams.setImport("@element-plus/icons", icon);
            PageParams.setSetupReturn(icon, icon);
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
