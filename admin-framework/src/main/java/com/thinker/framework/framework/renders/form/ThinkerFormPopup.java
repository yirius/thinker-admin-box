package com.thinker.framework.framework.renders.form;

import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerFormPopup extends LayoutAbstract {

    private String content;

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        PageParams.setImport("@/components/layer/index.vue", "ThinkerFormLayer", false);
        PageParams.setComponents("ThinkerFormLayer");
        PageParams.setProps("layer", "{type:Object,default:()=>{return{show:false,title:'',showButton:true}}}");

        String confirmEventName = getLayoutId()+"_layer_submit";
        PageParams.setMethods(confirmEventName, thinkerrender.adminbox.form.submitValidate.template(getLayoutId(),
                "this."+getLayoutId()+"_submitData();"
        ).render().toString());
        return "<ThinkerFormLayer :layer=\"layer\" @confirm=\""+confirmEventName+"\" ref=\""+getLayoutId()+"_layer\">"+content+"</ThinkerFormLayer>";
    }

}
