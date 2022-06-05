package com.thinker.framework.renders.assemblys.table;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.renders.ThinkerTable;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerTags;
import com.thinker.framework.renders.assemblys.form.plugins.ElButton;
import com.thinker.framework.renders.assemblys.page.ElImage;
import com.thinker.framework.renders.assemblys.page.ElPopconfirm;
import com.thinker.framework.renders.entity.table.*;
import com.thinker.framework.renders.entity.enums.VxeColumnEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class VxeColumn {

    // 记录一下列表的句柄
    private ThinkerTable tableIns;

    // 列的类型
    @ToRenderAttrs
    private VxeColumnEnum type;

    // 列字段名（注：属性层级越深，渲染性能就越差，例如：aa.bb.cc.dd.ee）
    @ToRenderAttrs
    private String field;

    // 列标题
    @ToRenderAttrs
    private String title;

    // 列宽度
    @ToRenderAttrs
    private String width;

    // 列宽度
    @ToRenderAttrs
    private String minWidth;

    // 列是否允许拖动列宽调整大小
    @ToRenderAttrs
    private Boolean resizable = true;

    // 默认是否显示
    @ToRenderAttrs
    private Boolean visible;

    // 将列固定在左侧或者右侧
    @ToRenderAttrs
    private String fixed;

    // 列对齐方式
    @ToRenderAttrs
    private String align;

    // 列对齐方式
    @ToRenderAttrs
    private String headerAlign;

    // 列对齐方式
    @ToRenderAttrs
    private String footerAlign;

    // 当内容过长时显示为省略号
    @ToRenderAttrs
    private String showOverflow;

    // 当内容过长时显示为省略号
    @ToRenderAttrs
    private String showHeaderOverflow;

    // 当内容过长时显示为省略号
    @ToRenderAttrs
    private String showFooterOverflow;

    // 当内容过长时显示为省略号
    @ToRenderAttrs
    private String className;

    // 给表头的单元格附加 className
    @ToRenderAttrs
    private String headerClassName;

    // 给表尾的单元格附加 className
    @ToRenderAttrs
    private String footerClassName;

    // 格式化显示内容
    @ToRenderAttrs(isEval = true)
    private String formatter;

    // 是否允许列排序
    @ToRenderAttrs
    private Boolean sortable;

    // 只对 sortable 有效，指定排序的字段
    @ToRenderAttrs(isEval = true)
    private String sortBy;

    // 排序的字段类型，比如字符串转数值等
    @ToRenderAttrs
    private String sortType;

    // 配置筛选条件（注：筛选只能用于列表，如果是树结构则过滤根节点）
    @ToRenderAttrs
    List<LabelValue> filters;

    // 只对 filters 有效，筛选是否允许多选
    @ToRenderAttrs
    private Boolean filterMultiple;

    // 只对 filters 有效，列的筛选方法，该方法的返回值用来决定该行是否显示
    @ToRenderAttrs(isEval = true)
    private String filterMethod;

    // 只对 filters 有效，自定义筛选重置方法
    @ToRenderAttrs(isEval = true)
    private String filterResetMethod;

    // 只对 filters 有效，自定义筛选复原方法
    @ToRenderAttrs(isEval = true)
    private String filterRecoverMethod;

    // 只对 filters 有效，自定义筛选复原方法
    @ToRenderAttrs(isJsonObject = true)
    private FilterRender filterRender;

    // 自定义单元格数据导出方法，返回自定义的值
    @ToRenderAttrs(isEval = true)
    private String exportMethod;

    // 自定义表尾单元格数据导出方法，返回自定义的值
    @ToRenderAttrs(isEval = true)
    private String footerExportMethod;

    // 标题前缀图标配置项
    @ToRenderAttrs(isJsonObject = true)
    private TitlePrefix titlePrefix;

    // 只对特定功能有效，单元格值类型
    @ToRenderAttrs
    private String cellType;

    // 默认的渲染器配置项
    @ToRenderAttrs(isJsonObject = true)
    private CellRender cellRender;

    // 可编辑渲染器配置项
    @ToRenderAttrs(isJsonObject = true)
    private EditRender editRender;

    // 内容渲染器配置项
    @ToRenderAttrs(isJsonObject = true)
    private ContentRender contentRender;

    // 只对 tree-config 配置时有效，指定为树节点
    @ToRenderAttrs
    private Boolean treeNode;

    // 额外的参数（可以用来存放一些私有参数）
    @ToRenderAttrs
    private Dict params;

    // 自定义列的唯一主键（注：99%的场景不应该设置，操作不正确将导致出现问题）
    @ToRenderAttrs
    private String colId;

    @ToRenderAttrs(isJsonObject = true)
    private VxeColumnSlots slots;

    public VxeColumn addDefaultSlot(RootRender render) {
        if(slots == null) slots = new VxeColumnSlots();
        slots.getDefaultTpl().add(render);
        return this;
    }

    public VxeColumn imgColumn(String imgName) {
        if(tableIns != null) {
            if(Validator.isEmpty(tableIns.getRowConfig().getHeight())) {
                tableIns.getRowConfig().setHeight("60");
            }
            if(Validator.isEmpty(tableIns.getShowOverflow())) {
                tableIns.setShowOverflow("[`eval`]true");
            }
        }
        return addDefaultSlot(new ElImage().columnShow(imgName).setId(getField().replace(".", "_") + "_img"));
    }

    public VxeColumn button(RunClosure<ElButton> buttonRun) {
        ElButton button = new ElButton();
        if(buttonRun != null) {
            buttonRun.run(button);
        }
        return addDefaultSlot(button);
    }

    public VxeColumn edit() {
        return edit(null);
    }

    public VxeColumn edit(RunClosure<ElButton> editButtonRun) {
        ElButton editButton = new ElButton().setLabel("编辑").setType("primary").setSize("small").setIcon("edit")
                .setOnClick("(e) => { vm.emit('openEditLayer', _Vue.toRaw(slotData.row)); }");
        if(editButtonRun != null) {
            editButtonRun.run(editButton);
        }
        return addDefaultSlot(editButton);
    }

    public VxeColumn delete() {
        return delete(null);
    }

    public VxeColumn delete(RunClosure<ElPopconfirm> deletePopconfirm) {
        ElPopconfirm elPopconfirm = new ElPopconfirm().delete();
        elPopconfirm.getReference().add(
                new ElButton().setLabel("删除").setType("danger").setSize("small").setIcon("delete")
        );
        if(deletePopconfirm != null) {
            deletePopconfirm.run(elPopconfirm);
        }
        return addDefaultSlot(elPopconfirm);
    }
}
