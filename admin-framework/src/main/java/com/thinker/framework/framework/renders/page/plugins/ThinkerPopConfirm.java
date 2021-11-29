package com.thinker.framework.framework.renders.page.plugins;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ThinkerPopConfirm extends LayoutAbstract {

    @ComponentAttr
    private String title;

    @ComponentAttr
    private String confirmButtonText;

    @ComponentAttr
    private String cancelButtonText;

    @ComponentAttr
    private ButtonType confirmButtonType;

    @ComponentAttr
    private ButtonType cancelButtonType;

    @ComponentAttr
    private String icon;

    @ComponentAttr
    private String iconColor;

    @ComponentAttr(prevStr = ":")
    private Boolean hideIcon;

    private String content;

    // 确定和取消的函数
    private String confirm;
    private String confirmArgs;

    public String getConfirmStr() {
        if(Validator.isNotEmpty(confirm)) {
            PageParams.setSetupScript("const " + getLayoutId() + "_pop_conform = " + confirm + ";");
            PageParams.setSetupReturn(getLayoutId() + "_pop_conform", getLayoutId() + "_pop_conform");
            return "@confirm=\""+getLayoutId() + "_pop_conform"+(Validator.isNotEmpty(confirmArgs)?"("+confirmArgs+")":"")+"\"";
        }

        return "";
    }

    /**
     * 确认删除
     * @return
     */
    public ThinkerPopConfirm confirmAllDelete(String tableId, String idKey, String url) {
        this.confirm = thinkerrender.adminbox.table.allDelete.template(tableId, idKey, url).render().toString();
        return this;
    }

    public ThinkerPopConfirm confirmAllDeleteUsePassword(String tableId, String idKey, String url) {
        this.confirm = thinkerrender.adminbox.table.allDeleteUsePwd.template(tableId, idKey, url).render().toString();
        return this;
    }

    public ThinkerPopConfirm confirmDelete(String tableId, String idKey, String url) {
        this.confirm = thinkerrender.adminbox.table.rowDelete.template(tableId, idKey, url).render().toString();
        return this;
    }

    public ThinkerPopConfirm confirmDeleteUsePassword(String tableId, String idKey, String url) {
        this.confirm = thinkerrender.adminbox.table.rowDeleteUsePwd.template(tableId, idKey, url).render().toString();
        return this;
    }

    /**
     * 取消的回调
     */
    private String cancel;

    public String getCancelStr() {
        if(Validator.isNotEmpty(cancel)) {
            PageParams.setSetupScript("const " + getLayoutId() + "_pop_cancel = " + cancel + ";");
            PageParams.setSetupReturn(getLayoutId() + "_pop_cancel", getLayoutId() + "_pop_cancel");
            return "@cancel=\""+getLayoutId() + "_pop_cancel\"";
        }

        return "";
    }

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        PageParams.setImport("@/api/request", "deleteRequest");
        PageParams.setImport("element-plus", "ElMessage", "ElMessageBox");

        return "<el-popconfirm "+ PageParams.strComponentAttrs(this) +" "+getConfirmStr()+" "+getCancelStr()+">"+slotStr("reference", null, content)+"</el-popconfirm>";
    }
}
