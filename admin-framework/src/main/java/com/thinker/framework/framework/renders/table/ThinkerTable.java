package com.thinker.framework.framework.renders.table;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.bo.TableSort;
import com.thinker.framework.framework.renders.bo.TableTreeProps;
import com.thinker.framework.framework.renders.enums.EffectType;
import com.thinker.framework.framework.renders.enums.LayoutSize;
import com.thinker.framework.framework.renders.page.ThinkerPage;
import com.thinker.framework.framework.renders.bo.TableLayerDataCase;
import com.thinker.framework.framework.renders.bo.TablePageCase;
import com.thinker.framework.framework.renders.form.ThinkerForm;
import com.thinker.framework.framework.renders.table.plugins.TableColumn;
import com.thinker.framework.framework.renders.table.plugins.TableToolbar;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ThinkerTable extends LayoutAbstract {

    // 表格名称
    private String title;

    /**
     * 显示的数据
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private List<Dict> data = new ArrayList<>();

    /**
     * 分页等的设置
     */
    @Setter(AccessLevel.NONE)
    @ComponentAttr(prevStr = "v-model:", isReactive = true)
    private TablePageCase page = new TablePageCase();

    /**
     * Table 的高度， 默认为自动高度。
     * 如果 height 为 number 类型，单位 px；如果 height 为 string 类型，则这个高度会设置为 Table 的 style.height 的值，Table 的高度会受控于外部样式。
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private String height;

    /**
     * Table 的最大高度。 合法的值为数字或者单位为 px 的高度。
     */
    @ComponentAttr(prevStr = ":")
    private Integer maxHeight;

    /**
     * 是否为斑马纹 table
     */
    @ComponentAttr(prevStr = ":")
    private boolean stripe = true;

    /**
     * 是否为斑马纹 table
     */
    @ComponentAttr(prevStr = ":")
    private Boolean border;

    /**
     * Table 的尺寸
     */
    @ComponentAttr
    private LayoutSize size;

    /**
     * 列的宽度是否自撑开
     */
    @ComponentAttr(prevStr = ":")
    private Boolean fit;

    /**
     * 是否显示表头
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showHeader;

    /**
     * 是否要高亮当前行
     */
    @ComponentAttr(prevStr = ":")
    private boolean highlightCurrentRow = true;

    /**
     * 行的 className 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,rowIndex}")
    private String rowClassName;

    /**
     * 行的 style 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,rowIndex}")
    private String rowStyle;

    /**
     * 列的 className 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,column,rowIndex,columnIndex}")
    private String cellClassName;

    /**
     * 列的 style 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,column,rowIndex,columnIndex}")
    private String cellStyle;

    /**
     * 表头行的 className 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,rowIndex}")
    private String headerRowClassName;

    /**
     * 表头行的 style 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,rowIndex}")
    private String headerRowStyle;

    /**
     * 表头单元格的 className 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,rowIndex}")
    private String headerCellClassName;

    /**
     * 表头单元格的 style 的回调方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,rowIndex}")
    private String headerCellStyle;

    /**
     * 行数据的 Key，用来优化 Table 的渲染
     */
    @ComponentAttr
    private String rowKey = "id";

    /**
     * 空数据时显示的文本内容
     */
    @ComponentAttr
    private String emptyText;

    /**
     * 是否默认展开所有行，当 Table 包含展开行存在或者为树形表格时有效
     */
    @ComponentAttr(prevStr = ":")
    private Boolean defaultExpandAll;

    /**
     * 可以通过该属性设置 Table 目前的展开行
     */
    @ComponentAttr(prevStr = ":")
    private List<Long> expandRowKeys;

    /**
     * 默认的排序列的 prop 和顺序
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private TableSort defaultSort;

    /**
     * tooltip effect 属性
     */
    @ComponentAttr
    private EffectType tooltipEffect;

    /**
     * 是否在表尾显示合计行
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showSummary;

    /**
     * 合计行第一列的文本
     */
    @ComponentAttr
    private String sumText;

    /**
     * 自定义的合计计算方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{columns,data}")
    private String summaryMethod;

    /**
     * 合并行或列的计算方法
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "{row,column,rowIndex,columnIndex}")
    private String spanMethod;

    /**
     * 在多选表格中，当仅有部分行被选中时，点击表头的多选框时的行为。 若为 true，则选中所有行；若为 false，则取消选择所有行
     */
    @ComponentAttr(prevStr = ":")
    private Boolean selectOnIndeterminate;

    /**
     * 展示树形数据时，树节点的缩进
     */
    @ComponentAttr(prevStr = ":")
    private Integer indent;

    /**
     * 是否懒加载子节点数据
     */
    @ComponentAttr(prevStr = ":")
    private Boolean lazy;

    /**
     * 加载子节点数据的函数，lazy 为 true 时生效，函数第二个参数包含了节点的层级信息
     */
    @ComponentAttr(prevStr = ":", isEvent = true, eventArgs = "row, treeNode, resolve")
    private String load;

    /**
     * 渲染嵌套数据的配置选项
     */
    @ComponentAttr(prevStr = ":", isRef = true)
    private TableTreeProps treeProps;

    /**
     * 是否显示index
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showIndex;

    /**
     * 是否选择显示内容
     */
    @ComponentAttr(prevStr = ":")
    private boolean showSelection = true;

    /**
     * 是否显示页数选择
     */
    @ComponentAttr(prevStr = ":")
    private Boolean showPage;

    /**
     * 界面显示样式
     */
    @ComponentAttr
    private String pageLayout = "total,sizes,prev,pager,next,jumper";

    /**
     * 是否显示loading
     */
    @ComponentAttr(prevStr = "v-", isRef = true)
    private boolean loading = true;

    /**
     * 页面可选择下拉数量
     */
    @ComponentAttr(prevStr = ":")
    private List<Integer> pageSizes;

    public ThinkerTable() { setLayoutId(getLayoutId() + "_table"); }

    public ThinkerTable(Closure closure) {
        setLayoutId(getLayoutId() + "_table");
        if(closure != null) closure.run(this);
    }

    //使用闭包的形式
    public interface Closure {
        void run(ThinkerTable thinkerTable);
    }

    /**
     * 列表内容项
     */
    @Setter(AccessLevel.NONE)
    private List<TableColumn> columns = new ArrayList<>();

    public TableColumn column(String prop, String label) {
        TableColumn column = new TableColumn(prop, label, this);
        columns.add(column);
        return column;
    }

    /**
     * 当用户手动勾选数据行的 Checkbox 时触发的事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "selection,row")
    private String select;

    /**
     * 当用户手动勾选全选 Checkbox 时触发的事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "selection")
    private String selectAll;

    /**
     * 当单元格 hover 进入时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,cell,event")
    private String cellMouseEnter;

    /**
     * 当单元格 hover 退出时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,cell,event")
    private String cellMouseLeave;

    /**
     * 当某个单元格被点击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,cell,event")
    private String cellClick;

    /**
     * 当某个单元格被双击击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,cell,event")
    private String cellDbclick;

    /**
     * 当某个单元格被鼠标右键点击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,cell,event")
    private String cellContextmenu;

    /**
     * 当某一行被点击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,event")
    private String rowClick;

    /**
     * 当某一行被鼠标右键点击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,event")
    private String rowContextmenu;

    /**
     * 当某一行被双击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,column,event")
    private String rowDblclick;

    /**
     * 当某一列的表头被点击时会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "column,event")
    private String headerClick;

    /**
     * 当某一列的表头被鼠标右键点击时触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "column,event")
    private String headerContextmenu;

    /**
     * column 的 key， 如果需要使用 filter-change 事件，则需要此属性标识是哪个 column 的筛选条件
     */
    @ComponentAttr(isEvent = true, eventArgs = "filters")
    private String filterChange;

    /**
     * 当表格的当前行发生变化的时候会触发该事件，如果要高亮当前行，请打开表格的 highlight-current-row 属性
     */
    @ComponentAttr(isEvent = true, eventArgs = "currentRow,oldCurrentRow")
    private String currentChange;

    /**
     * 当拖动表头改变了列的宽度的时候会触发该事件
     */
    @ComponentAttr(isEvent = true, eventArgs = "newWidth,oldWidth,column,event")
    private String headerDragend;

    /**
     * 当用户对某一行展开或者关闭的时候会触发该事件（展开行时，回调的第二个参数为 expandedRows；树形表格时第二参数为 expanded）
     */
    @ComponentAttr(isEvent = true, eventArgs = "row,expandedRows")
    private String expandChange;

    /**
     * 排序事件
     */
    private String sortChange = "";

    public String getSortChangeStr() {
        PageParams.setMethods(getLayoutId() + "_sortChange", "({ column, prop, order }) {" +
                "   this." + getLayoutId() + "_page.sort = prop;" +
                "   this." + getLayoutId() + "_page.order = order;" +
                "   " + sortChange +
                "   this." + getLayoutId() + "_getTableData(true);" +
                "}");

        return "@sortChange=\""+getLayoutId()+"_sortChange\"";
    }

    private String append;

    public String getAppendStr() {
        if(Validator.isNotEmpty(append)) {
            return slotStr("append", "", append);
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
        PageParams.setImport("@/components/table/index.vue", "Table", false);
        PageParams.setComponents("Table");

        StrBuilder columnStr = new StrBuilder();
        columns.forEach(columnStr::append);

        // 搜索的form内容需要列在getTableData前，否则无法访问xxxx_form_formValue;
        String searchStr = search == null ? "" : "<div style='padding: 15px 15px 0 15px'>"+search.render()+"</div>";
        String componentAttr = PageParams.strComponentAttrs(this), getTableDataAttr = getTableData();

        return  searchStr + (toolbar == null ? "" : toolbar.render()) +
                "<div class=\"layout-container-table\">\n" +
                "   <Table ref=\""+getLayoutId()+"\" "+componentAttr+" "+getTableDataAttr+" "+getSortChangeStr()+">\n" +
                "       " + columnStr +
                "       " + getAppendStr() +
                "   </Table>\n" +
                getEditComponent(getTableDataAttr) + "\n" +
                "</div>\n";
    }

    // api网址，请求地址
    private String api;
    // 对数据进行循环处理
    private String eachData = "";
    // 搜索的附加项
    private String tableQuery = "";
    // 搜索的内容
    private String selectionChange = "";

    /**
     * 设置restful请求地址
     * @return
     */
    private String getTableData() {
        if(Validator.isNotEmpty(api)) {
            PageParams.setImport("@/api/request", "getRequest");

            PageParams.setSetupScript(thinkerrender.adminbox.table.getTableData.template(
                    getLayoutId(), api, (search != null ? "..."+search.getLayoutId()+"_formValue.value," : "") + tableQuery, eachData
            ).render().toString());

            PageParams.setSetupReturn(getLayoutId()+"_getTableData", getLayoutId()+"_getTableData");
            // 当前选择的数据记录一下
            PageParams.createRef(getLayoutId() + "_selectedData", "[]");
            // 记录数据的函数
            PageParams.setSetupScript("const " + getLayoutId() + "_selectedChange = (selection) => {\n" +
                    "   " + getLayoutId() + "_selectedData.value = selection;\n" +
                            selectionChange +
                    "};\n");
            PageParams.setSetupReturn(getLayoutId() + "_selectedChange", getLayoutId() + "_selectedChange");

            return "@getTableData=\""+getLayoutId()+"_getTableData\" @selection-change=\""+getLayoutId()+"_selectedChange\" ";
        }

        return "";
    }

    /**
     * 头部的设置
     */
    private TableToolbar toolbar = null;

    public TableToolbar toolbar() {
        if(toolbar == null) {
            toolbar = initLayoutAbstract(new TableToolbar(this));
        }
        return toolbar;
    }

    // 设置编辑界面
    private String editUrl;

    // 弹出的界面相关设置，需要有默认参数
    private TableLayerDataCase layerData = new TableLayerDataCase();

    private String getEditComponent(String getTableDataAttr) {
        // 判断editUrl是否存在
        if(Validator.isNotEmpty(editUrl)) {
            String cameEditLayer = StrUtil.toCamelCase(getLayoutId() + "_edit_layer");
            PageParams.setImport(editUrl, cameEditLayer, false);
            PageParams.setComponents(cameEditLayer);
            PageParams.createReactive(getLayoutId() + "_edit_layer_data", JSON.toJSONString(layerData));

            return "<" + cameEditLayer + " :layer=\"" + getLayoutId() + "_edit_layer_data\" " + getTableDataAttr + " v-if=\"" + getLayoutId() + "_edit_layer_data.show\" />";
        }
        return "";
    }

    /**
     * 搜索的内容
     */
    @Setter(AccessLevel.NONE)
    private ThinkerForm search = null;

    public ThinkerForm search(ThinkerForm.Closure closure) {
        search = initLayoutAbstract(new ThinkerForm());
        search.getSubmitBtn().setClick("this." + getLayoutId() + "_getTableData(true)");
        if(closure != null) closure.run(search);
        return search.clearPopup();
    }

    /**
     * 返回界面内容，可以进行二次渲染
     * @param closure
     * @return
     */
    public ThinkerPage page(ThinkerPage.Closure closure) {
        return new ThinkerPage(thinkerPage -> {
            thinkerPage.getLayouts().add(this);
            if(closure != null) {
                closure.run(thinkerPage);
            }
        });
    }

    public ThinkerPage page() {
        return this.page(null);
    }

    @Override
    public String toString() {
        return String.valueOf(render());
    }
}
