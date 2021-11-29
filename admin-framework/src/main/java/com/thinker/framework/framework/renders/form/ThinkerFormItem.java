package com.thinker.framework.framework.renders.form;

import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.FormItemSize;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerFormItem extends LayoutAbstract {

    //使用闭包的形式
    public interface Closure {
        void run(ThinkerFormItem thinkerFormItem);
    }

    @ComponentAttr
    private String prop;

    @ComponentAttr
    private String label;

    @ComponentAttr
    private String labelWidth;

    @ComponentAttr(prevStr = ":")
    private Boolean required;

    @ComponentAttr
    private String error;

    @ComponentAttr(prevStr = ":")
    private Boolean showMessage;

    @ComponentAttr(prevStr = ":")
    private Boolean inlineMessage;

    @ComponentAttr
    private FormItemSize size = FormItemSize.NORMAL;

    // 渲染的内容
    private String content;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        return "<el-form-item " + PageParams.strComponentAttrs(this) + ">\n"+content+"\n</el-form-item>\n";
    }
}
