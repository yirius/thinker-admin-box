package com.thinker.framework.renders;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.abstracts.form.FormPluginRender;
import com.thinker.framework.renders.aspects.ToRenderAttrs;
import com.thinker.framework.renders.assemblys.ThinkerTags;
import com.thinker.framework.renders.assemblys.form.plugins.ElSelect;
import com.thinker.framework.renders.assemblys.page.ElImageViewer;
import com.thinker.framework.renders.assemblys.page.LayerBox;
import com.thinker.framework.renders.assemblys.table.VxeColumn;
import com.thinker.framework.renders.entity.table.*;
import com.thinker.framework.renders.entity.RenderResult;
import com.thinker.framework.renders.entity.enums.VxeColumnEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ThinkerTable extends RootRender {

    public ThinkerTable() {
        setId(DefineComponent.getRenderId() + "_" + getId());
    }

    /**
     * 便捷方法运行
     * @param runClosure
     * @return
     */
    public ThinkerTable runClosure(RunClosure<ThinkerTable> runClosure) {
        runClosure.run(this);
        return this;
    }

    @Setter(AccessLevel.NONE)
    private List<VxeColumn> columns = new ArrayList<>();

    public VxeColumn column(String field, String title) {
        VxeColumn vxeColumn = new VxeColumn().setTitle(title).setField(field).setTableIns(this);
        columns.add(vxeColumn);
        return vxeColumn;
    }

    public VxeColumn checkbox() {
        return column(null, "id").setType(VxeColumnEnum.CHECKBOX).setWidth("80px");
    }

    public VxeColumn seq() {
        return column(null, "#").setType(VxeColumnEnum.SEQ);
    }

    public VxeColumn radio() {
        return column(null, "id").setType(VxeColumnEnum.RADIO).setWidth("80px");
    }

    public VxeColumn expand() {
        return column(null, "#").setType(VxeColumnEnum.EXPAND);
    }

    @ToRenderAttrs
    private Object data;

    @ToRenderAttrs
    private String height = "auto";

    @ToRenderAttrs
    private String maxHeight;

    @ToRenderAttrs
    private Boolean autoResize = true;

    @ToRenderAttrs
    private Boolean syncResize;

    @ToRenderAttrs
    private Boolean stripe = true;

    @ToRenderAttrs
    private Boolean border;

    @ToRenderAttrs
    private Boolean round;

    @ToRenderAttrs
    private String size = "small";

    @ToRenderAttrs(isEval = true)
    private String loading;

    @ToRenderAttrs
    private String align = "center";

    @ToRenderAttrs
    private String headerAlign;

    @ToRenderAttrs
    private String footerAlign;

    @ToRenderAttrs
    private Boolean showHeader;

    @ToRenderAttrs(isEval = true)
    private String rowClassName;

    @ToRenderAttrs(isEval = true)
    private String cellClassName;

    @ToRenderAttrs(isEval = true)
    private String headerRowClassName;

    @ToRenderAttrs(isEval = true)
    private String headerCellClassName;

    @ToRenderAttrs(isEval = true)
    private String footerRowClassName;

    @ToRenderAttrs(isEval = true)
    private String footerCellClassName;

    @ToRenderAttrs
    private Boolean showFooter;

    @ToRenderAttrs(isEval = true)
    private String footerMethod;

    @ToRenderAttrs(isEval = true)
    private String mergeCells;

    @ToRenderAttrs(isEval = true)
    private String mergeFooterItems;

    @ToRenderAttrs
    private String showOverflow = "tooltip";

    @ToRenderAttrs
    private String showHeaderOverflow = "tooltip";

    @ToRenderAttrs
    private String showFooterOverflow;

    @ToRenderAttrs
    private Boolean keepSource = true;

    // 列配置信息
    @ToRenderAttrs(isJsonObject = true)
    private ColumnConfig columnConfig = new ColumnConfig().setMinWidth("100px");

    // 列配置信息
    @ToRenderAttrs(isJsonObject = true)
    private RowConfig rowConfig = new RowConfig();

    // 列配置信息
    @ToRenderAttrs(isJsonObject = true)
    private ResizableConfig resizableConfig;

    // 列配置信息
    @ToRenderAttrs(isJsonObject = true)
    private SeqConfig seqConfig;

    // 列配置信息
    @ToRenderAttrs(isJsonObject = true)
    private SortConfig sortConfig = new SortConfig();

    // 列配置信息
    @ToRenderAttrs(isJsonObject = true)
    private FilterConfig filterConfig = new FilterConfig();

    @ToRenderAttrs(isJsonObject = true)
    private ExportConfig exportConfig = new ExportConfig();

    @ToRenderAttrs(isJsonObject = true)
    private ImportConfig importConfig;

    public ThinkerTable importData(String url) {
        if(importConfig == null) {
            importConfig = new ImportConfig();
        }
        importConfig.setImportMethod("(e) => {" +
                "   return _uploadHttpRequestApi({" +
                "       action: \""+url+"\"," +
                "       filename: \"file\"," +
                "       file: e.file," +
                "       data: {}," +
                "       method: \"POST\"," +
                "       headers: {}" +
                "   }).then(response => {" +
                "       " +
                "   }).catch(response => {\n" +
                "       _VXETable.modal.message({ content: '导入失败，请检查数据是否正确！', status: 'error' });" +
                "   });" +
                "}");
        return this;
    }

    @ToRenderAttrs(isJsonObject = true)
    private PrintConfig printConfig = new PrintConfig();

    @ToRenderAttrs(isJsonObject = true)
    private RadioConfig radioConfig;

    @ToRenderAttrs(isJsonObject = true)
    private CheckboxConfig checkboxConfig = new CheckboxConfig();

    @ToRenderAttrs(isJsonObject = true)
    private TooltipConfig tooltipConfig;

    @ToRenderAttrs(isJsonObject = true)
    private ExpandConfig expandConfig;

    @ToRenderAttrs(isJsonObject = true)
    private TreeConfig treeConfig;

    @ToRenderAttrs(isJsonObject = true)
    private KeyboardConfig keyboardConfig;

    @ToRenderAttrs(isJsonObject = true)
    private EditConfig editConfig = new EditConfig();

    @ToRenderAttrs(isJsonObject = true)
    private ValidConfig validConfig;

    @ToRenderAttrs(isJsonObject = true)
    private Map<String, List<EditRuleItem>> editRules;

    @ToRenderAttrs
    private String emptyText;

    @ToRenderAttrs(isJsonObject = true)
    private EmptyRender emptyRender;

    @ToRenderAttrs(isJsonObject = true)
    private CustomConfig customConfig = new CustomConfig();

    @ToRenderAttrs(isJsonObject = true)
    private ScrollX scrollX;

    @ToRenderAttrs(isJsonObject = true)
    private ScrollY scrollY = new ScrollY();

    @ToRenderAttrs
    private Dict params;

    @ToRenderAttrs(isJsonObject = true)
    private FormConfig formConfig;

    @ToRenderAttrs(isJsonObject = true)
    private ToolbarConfig toolbarConfig = new ToolbarConfig();

    public ToolbarConfig toolbar() {
        return toolbarConfig;
    }

    @ToRenderAttrs(isJsonObject = true)
    private PagerConfig pagerConfig = new PagerConfig();

    @ToRenderAttrs(isJsonObject = true)
    private ProxyConfig proxyConfig = new ProxyConfig();

    // table的相关事件
    private Map<String, String> events = new HashMap<>();

    // 请求地址
    private String api;

    // 编辑文件的地址
    private String editUrl;

    public ThinkerTable setEditUrl(String editUrl) {
        this.editUrl = editUrl;
        formLayer = new LayerBox().addFormPage(getId()+"_formlayer", editUrl, 0);
        return this;
    }

    // 保存一下弹窗
    @Setter(AccessLevel.NONE)
    private LayerBox formLayer;

    // 保存一下formItem
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private ThinkerForm thinkerForm;

    // 添加搜索
    public ThinkerForm search(RunClosure<ThinkerForm> thinkerFormRunClosure) {
        thinkerForm = new ThinkerForm();
        thinkerFormRunClosure.run(thinkerForm);
        return thinkerForm;
    }

    @Override
    public RenderResult render() {
        beforeRender();

        Map<String, List<RenderResult>> slotRender = new HashMap<>();
        slots.forEach((s, rootRenders) -> slotRender.put(s, rootRenders.stream().map(RootRender::render).collect(Collectors.toList())));

        return afterRender(RenderResult.create(
                component, attrs,
                children.stream().map(rootRender -> {
                    rootRender.setId(this.getAttrs().getStr("id") + "_" + rootRender.getId());
                    return rootRender.render();
                }).collect(Collectors.toList()),
                slotRender
        ));
    }

    @Override
    public void beforeRender() {
        setComponent("vxeTableBox");

        if(Validator.isEmpty(id)) this.id = DefineComponent.getRenderId();

        // 渲染表单
        if(thinkerForm != null) {
            List<FormConfigItems> formConfigItems = new ArrayList<>();
            List<RenderResult> formResult = thinkerForm.render().getChildren();

            boolean needCollapseNode = false;
            for (int i = 0; i < formResult.size(); i++) {
                // 删除更新数据，不需要
                formResult.get(i).getChildren().get(0).getAttrs().remove("modelValue");
                formResult.get(i).getChildren().get(0).getAttrs().remove("onUpdate:modelValue");

                Dict renderEvent = Dict.create();
                formResult.get(i).getChildren().get(0).getAttrs().forEach((s, o) -> {
                    if(s.startsWith("on") && String.valueOf(o).startsWith("[`eval`]")) {
                        // 说明是事件
                        renderEvent.set(StrUtil.lowerFirst(s.substring(2)), String.valueOf(o).replace("[`eval`]", ""));
                    }
                });

                // 组装渲染器
                EditRender editRender = new EditRender().setName(
                        formResult.get(i).getChildren().get(0).getComponent()
                ).setProps(
                        formResult.get(i).getChildren().get(0).getAttrs()
                ).setEvents(renderEvent);

                // 如果是Select，需要填充内容
                if(editRender.getName().equals("ElSelect")) {
                    editRender.setOptions(((ElSelect) thinkerForm.getChildren().get(i).getChildren().get(0)).getOptions());
                }

                // 判断是否存在折叠
                Boolean folding = ((FormPluginRender<?>) thinkerForm.getChildren().get(i).getChildren().get(0)).getFolding();

                // 添加新的参数
                formConfigItems.add(
                        new FormConfigItems()
                                .setSpan(12)
                                .setTitle(formResult.get(i).getAttrs().getStr("label"))
                                .setField(formResult.get(i).getChildren().get(0).getAttrs().getStr("name"))
                                .setItemRender(editRender)
                                .setFolding(folding)
                );

                if(folding != null && folding) needCollapseNode = true;
            }

            formConfigItems.add(new FormConfigItems().setSpan(24).setAlign("center").setCollapseNode(needCollapseNode).setItemRender(
                    new EditRender().setName("$buttons").setChildren(new ArrayList<Dict>() {
                        {
                            add(Dict.create().set("props", Dict.create().set("type", "submit").set("content", "提交").set("status", "primary")));
                            add(Dict.create().set("props", Dict.create().set("type", "reset").set("content", "重置")));
                        }
                    })
            ));

            if(formConfig == null) formConfig = new FormConfig().setTitleWidth("120px");
            formConfig.setItems(formConfigItems);
        }

        // 如果设置了api，直接填充
        if(Validator.isNotEmpty(api)) {
            if(proxyConfig.getAjax() == null) {
                proxyConfig.setAjax(new ProxyConfigAjax());
            }

            if(Validator.isEmpty(proxyConfig.getAjax().getQuery())) {
                proxyConfig.getAjax().setQuery("(queryData) => { " +
                        "   const queryParams = Object.assign({}, queryData.form);" +
                        "   const firstSort = queryData.sorts[0];" +
                        "   if (firstSort) {" +
                        "       queryParams.sort = firstSort.property;" +
                        "       queryParams.order = firstSort.order;" +
                        "   }" +
                        "   queryData.filters.forEach(({ property, values }) => {" +
                        "       queryParams[property] = values.join(',');" +
                        "   });" +
                        "   queryParams.limit = queryData.page.pageSize;" +
                        "   queryParams.page = queryData.page.currentPage;" +
                        "   return _RequestApi.getRequest(\""+api+"\", queryParams).then(response => response.data);" +
                        "}");
            }

            if(Validator.isEmpty(proxyConfig.getAjax().getDelete())) {
                proxyConfig.getAjax().setDelete("(deleteData) => {" +
                        "   var deleteIds = [], extInfo = {};" +
                        "   if(deleteData.button&&deleteData.button.extInfo) extInfo = deleteData.button.extInfo;" +
                        "   deleteData.body.removeRecords.forEach(item => deleteIds.push(item.id));" +
                        "   return _RequestApi.deleteRequest(\""+api+"\", {ids: deleteIds.join(','), ...extInfo}).then(response => {" +
                        "       (_Vue.unref(vxeGrid||deleteData)).commitProxy('query');" +
                        "       return response.data;" +
                        "   });" +
                        "}");
            }
        }

        if(columns.size() > 0) {
            attrs.set("columns", columns.stream().map(vxeColumn -> {
                Dict columnAttr = Dict.create();
                toRenderAttrs(columnAttr, vxeColumn);
                return columnAttr;
            }).collect(Collectors.toList()));
        }

        Dict tableConfig = Dict.create();
        toRenderAttrs(tableConfig, this);
        attrs.set("tableConfig", tableConfig);
        // 设置一下id
        attrs.set("id", getId());

        // 加载事件
        Dict eventDict = Dict.create();
        events.forEach((s, s1) -> {
            eventDict.put(s, "[`eval`]" + s1);
        });
        attrs.set("events", eventDict);

        if(Validator.isNotEmpty(editUrl)) {
            setOnEvent("openEditLayer", "(rowData) => {" +
                    "   const formLayer = fieldIdOperate.find(\""+getId()+"_formlayer\");" +
                    "   formLayer.children[0].attrs.useIdKey = rowData.id;" +
                    "   formLayer.children[0].attrs.afterRender = (nextProp) => { " +
                    "       var sendData = {};" +
                    "       if(nextProp.renderValue.getDataBefore) {\n" +
                    "           eval(nextProp.renderValue.getDataBefore);\n" +
                    "       }" +
                    "       _RequestApi.getRequest(\""+api+"/\"+rowData.id, sendData).then(response => { " +
                    "           for(var i in response.data) {" +
                    "               if(typeof nextProp.modelRefsValue[i] != 'undefined') {" +
                    "                   nextProp.modelRefsValue[i].value = response.data[i];" +
                    "               }" +
                    "           }" +
                    "           if(nextProp.renderValue.renderDataReady) {\n" +
                    "               eval(nextProp.renderValue.renderDataReady);\n" +
                    "           }" +
                    "       }) " +
                    "   };" +
                    "   formLayer.attrs.layer.extInfo = rowData;" +
                    "   formLayer.attrs.layer.show = true; " +
                    "}");

            setOnEvent("openAddLayer", "(rowData) => {" +
                    "   const formLayer = fieldIdOperate.find(\""+getId()+"_formlayer\");" +
                    "   formLayer.children[0].attrs.useIdKey = null;" +
                    "   formLayer.children[0].attrs.afterRender = (nextProp) => {" +
                    "       for(var valueKey in nextProp.formValue) {" +
                    "           if(_AdminIs.isArray(nextProp.formValue[valueKey])) {" +
                    "               nextProp.modelRefsValue[valueKey].value = [];" +
                    "           } else {" +
                    "               nextProp.modelRefsValue[valueKey].value = null;" +
                    "           }" +
                    "       }" +
                    "       if(nextProp.renderValue.renderDataReady) {\n" +
                    "           eval(nextProp.renderValue.renderDataReady);\n" +
                    "       }" +
                    "   };" +
                    "   formLayer.attrs.layer.extInfo = {};" +
                    "   formLayer.attrs.layer.show = true; " +
                    "}");
        }

        // 补充一下几个props参数
        getAttrs().set("modelRefsValue", "[`eval`]props.modelRefsValue");
        getAttrs().set("useIdKey", "[`eval`]props.useIdKey");
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }

    // 界面相关
    public ThinkerResponse page() {
        return page(null);
    }

    public ThinkerResponse page(ThinkerPage.RunClosure runClosure) {
        return new ThinkerPage(thinkerPage -> {
            thinkerPage.getChildren().add(this);
            if(formLayer != null) {
                // 添加一下编辑界面
                thinkerPage.getChildren().add(formLayer);
            }

            thinkerPage.getChildren().add(new ThinkerTags().runClosure(rootRender -> {
                rootRender.getChildren().add(new ElImageViewer().setModelValue(getId()+"_img_viewer"));
            }));

            if(runClosure != null) {
                runClosure.run(thinkerPage);
            }
        }).render();
    }
}
