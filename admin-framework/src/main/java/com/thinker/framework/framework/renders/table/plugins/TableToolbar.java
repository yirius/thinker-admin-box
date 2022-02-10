package com.thinker.framework.framework.renders.table.plugins;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.renders.form.assemblys.Button;
import com.thinker.framework.framework.renders.table.ThinkerTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TableToolbar extends LayoutAbstract {

    @Setter(AccessLevel.NONE)
    private ThinkerTable thinkerTable;

    public TableToolbar(ThinkerTable thinkerTable) {
        this.thinkerTable = thinkerTable;
    }

    /**
     * 所有左侧的按钮
     */
    List<Button> buttons = new ArrayList<>();

    public Button button(String prop, String label) {
        Button button = initLayoutAbstract(new Button(prop, label));
        buttons.add(button);
        return button;
    }

    /**
     * 新增按钮
     * @return
     */
    public TableToolbar add() {
        return add(null);
    }

    public TableToolbar add(Button.Closure closure) {
        Button addBtn = this.button(getLayoutId() + "_toolbar_add", "新增").setIcon("Plus").setClick(
                "this."+getLayoutId()+"_edit_layer_data.title = '新增数据';\n" +
                        "this."+getLayoutId()+"_edit_layer_data.show = true;\n" +
                        "delete this."+getLayoutId()+"_edit_layer_data.rowData;\n" +
                        "delete this."+getLayoutId()+"_edit_layer_data.rowIdKey;\n"
        ).setType(ButtonType.PRIMARY);
        if(closure != null) closure.run(addBtn);
        return this;
    }

    /**
     * 批量删除按钮
     * @return
     */
    public TableToolbar delete() {
        return delete(null);
    }

    public TableToolbar delete(Button.Closure closure) {
        Button deleteBtn = this.button(getLayoutId() + "_toolbar_delete", "批量删除").setIcon("Delete").setType(ButtonType.DANGER);
        deleteBtn.setDisabled(getLayoutId() + "_selectedData.length == 0");
        deleteBtn.openPopConfirm().setTitle("是否确定批量删除数据?")
                .confirmAllDeleteUsePassword(thinkerTable.getLayoutId(), "id", thinkerTable.getApi());
        if(closure != null) closure.run(deleteBtn);
        return this;
    }

    /**
     * 所有右侧的工具
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<LayoutAbstract> tools = new ArrayList<>();

    public <T extends LayoutAbstract> T addTool(T tool) {
        tools.add(initLayoutAbstract(tool));
        return tool;
    }

    public TableToolbar defaultTools() {
        return this.refresh().exportXlsx();
    }

    /**
     * 刷新按钮
     * @return
     */
    @Setter(AccessLevel.NONE)
    private Button refreshBtn = null;

    public TableToolbar refresh() {
        return refresh(null);
    }

    public TableToolbar refresh(Button.Closure closure) {
        if(refreshBtn == null) {
            refreshBtn = addTool(new Button("tool_refresh", "刷新"))
                    .setType(ButtonType.TEXT).setIcon("Refresh").setClick("this."+getLayoutId()+"_getTableData()");
        }

        if(closure != null) {
            closure.run(refreshBtn);
        }

        return this;
    }

    /**
     * 导出按钮
     * @return
     */
    @Setter(AccessLevel.NONE)
    private Button exportBtn = null;

    public TableToolbar exportXlsx() {
        return exportXlsx(null);
    }

    public TableToolbar exportXlsx(Button.Closure closure) {
        if(exportBtn == null) {
            PageParams.setImport("@/components/xlsx/index", "aoaToSheetXlsx", "jsonToSheetXlsx");

            exportBtn = addTool(new Button("tool_exportXlsx", "导出"))
                    .setType(ButtonType.TEXT).setIcon("Download")
                    .setClick(exportXlsxStr("", ""));
        }

        if(closure != null) {
            closure.run(exportBtn);
        }

        return this;
    }

    /**
     * 如果需要设置参数，可以在closure里设置
     * @param exportFileName
     * @param exportUrl
     * @return
     */
    public String exportXlsxStr(String exportFileName, String exportUrl) {
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        thinkerTable.getColumns().forEach(tableColumn -> columns.put(tableColumn.getProp(), tableColumn.getLabel()));

        return thinkerrender.adminbox.table.exportXlsx.template(
                exportFileName, thinkerTable.getLayoutId(), JSON.toJSONString(columns), Validator.isEmpty(exportUrl) ? "\"\"" : exportUrl
        ).render().toString();
    }

    /**
     * 各组件需要继承的接口
     *
     * @return
     */
    @Override
    public Object render() {
        StrBuilder strBuilder = new StrBuilder();
        buttons.forEach(button -> strBuilder.append(button.render()));

        StrBuilder toolBuilder = new StrBuilder();
        tools.forEach(layoutAbstract -> toolBuilder.append(layoutAbstract.render()));

        return thinkerrender.adminbox.table.toolbar.template(strBuilder.toStringAndReset(), toolBuilder.toStringAndReset()).render().toString();
    }
}
