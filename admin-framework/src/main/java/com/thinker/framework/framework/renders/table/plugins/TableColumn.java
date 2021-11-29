package com.thinker.framework.framework.renders.table.plugins;

import cn.hutool.core.text.StrBuilder;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.renders.form.assemblys.Button;
import com.thinker.framework.framework.renders.table.ThinkerTable;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TableColumn extends LayoutAbstract {

    @Setter(AccessLevel.NONE)
    private ThinkerTable thinkerTable;

    @ComponentAttr
    private String prop;

    @ComponentAttr
    private String label;

    @ComponentAttr
    private String width;

    @ComponentAttr
    private String minWidth;

    @ComponentAttr
    private String fixed;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{column,$index}")
    private String renderHeader;

    /**
     * 对应列是否可以排序， 如果设置为 'custom'，则代表用户希望远程排序，需要监听 Table 的 sort-change 事件
     */
    @ComponentAttr
    @Setter(AccessLevel.NONE)
    private String sortable;

    public TableColumn openSortable() {
        this.sortable = "custom";
        return this;
    }

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "a,b")
    private String sortMethod;

    @ComponentAttr(prevStr = ":")
    private Boolean resizable;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "row,column,cellValue,index")
    private String formatter;

    @ComponentAttr(prevStr = ":")
    private Boolean showOverflowTooltip;

    @ComponentAttr
    private String align;

    @ComponentAttr
    private String headerAlign;

    @ComponentAttr
    private String className;

    @ComponentAttr
    private String labelClassName;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "row,index")
    private String selectable;

    @ComponentAttr(prevStr = ":")
    private Boolean reserveSelection;

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<TextValue> filters;

    @ComponentAttr
    private String filterPlacement;

    @ComponentAttr(prevStr = ":")
    private Boolean filterMultiple;

    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "value,row,column")
    private Boolean filterMethod;

    @ComponentAttr(prevStr = ":", isRef = true)
    private List<Long> filterValue;

    public TableColumn(String prop, String label, ThinkerTable thinkerTable) {
        setProp(prop);
        setLabel(label);
        this.thinkerTable = thinkerTable;
        // 重制名称
        setLayoutId(thinkerTable.getLayoutId() + "_" + prop);
    }

    /**
     * 设置内部模板
     */
    private List<LayoutAbstract> defaultTemplate = new ArrayList<>();

    public String defaultTemplateStr() {
        if(defaultTemplate.size() > 0) {
            StrBuilder strBuilder = new StrBuilder();
            defaultTemplate.forEach(layoutAbstract -> strBuilder.append(layoutAbstract.render()));
            return slotStr("default", "scope", strBuilder.toStringAndReset());
        }
        return "";
    }

    /**
     * 加入一个按钮
     * @param prop
     * @param label
     * @return
     */
    public Button button(String prop, String label) {
        Button button = new Button(prop, label);
        button.setLayoutId(getLayoutId());
        defaultTemplate.add(button);
        return button;
    }

    /**
     * 编辑相关的按钮
     */
    @Setter(AccessLevel.NONE)
    private Button editBtn = null;

    public TableColumn edit() {
        return edit("编辑数据", "id", null);
    }

    public TableColumn edit(String title, String idKeyName, Button.Closure closure) {
        if(editBtn == null) {
            editBtn = button("edit", "编辑").setClickArgs("scope").setClick(
                    "this."+thinkerTable.getLayoutId()+"_edit_layer_data.title='"+title+"';\n"+
//                        这里预留，如果有直接渲染，可以使用
//                        "this."+getLayoutId()+"_edit_layer_data.rowData=scope.row;\n"+
                            "this."+thinkerTable.getLayoutId()+"_edit_layer_data.rowIdKey=scope.row['"+idKeyName+"'];\n"+
                            "this."+thinkerTable.getLayoutId()+"_edit_layer_data.show=true;\n"
            );
        }

        if(closure != null) {
            closure.run(editBtn);
        }

        return this;
    }

    /**
     * 删除相关的按钮
     */
    @Setter(AccessLevel.NONE)
    private Button deleteBtn = null;

    public TableColumn delete() {
        return delete(null);
    }

    public TableColumn delete(Button.Closure closure) {
        if(deleteBtn == null) {
            PageParams.setImport("@/api/request", "deleteRequest");
            PageParams.setImport("element-plus", "ElMessage");

            deleteBtn = button("delete", "删除").setClickArgs("scope").setType(ButtonType.DANGER);

            deleteBtn.openPopConfirm().setTitle("是否确定删除该条数据?").setConfirmArgs("scope")
                    .confirmDeleteUsePassword(thinkerTable.getLayoutId(), "id", thinkerTable.getApi());
        }

        if(closure != null) {
            closure.run(deleteBtn);
        }

        return this;
    }

    /**
     * 头部内容
     */
    private List<LayoutAbstract> headerTemplate = new ArrayList<>();

    public String headerTemplateStr() {
        if(headerTemplate.size() > 0) {
            StrBuilder strBuilder = new StrBuilder();
            headerTemplate.forEach(layoutAbstract -> strBuilder.append(layoutAbstract.render()));
            return slotStr("header", "scope", strBuilder.toStringAndReset());
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
        return "<el-table-column "+ PageParams.strComponentAttrs(this) +" >" +
                headerTemplateStr() +
                defaultTemplateStr() +
                "</el-table-column>";
    }

    @Override
    public String toString() {
        return String.valueOf(render());
    }
}
