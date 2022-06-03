package com.thinker.framework.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.YmlDynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.extend.ThinkerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/thinker/monitor")
public class MonitorController extends ThinkerController {

    @RequestMapping(value = "/loginLogs")
    public ThinkerResponse loginLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs?is_null_user=1");

            thinkerTable.search(thinkerForm -> {
                thinkerForm.input("content", "内容搜索");
            });

            thinkerTable.column("stages", "运行方法");
            thinkerTable.column("typeName", "归属类型");
            thinkerTable.column("content", "提交内容");
            thinkerTable.column("message", "输出数据");
            thinkerTable.column("createTime", "记录时间").setWidth("160px");

        }).page();
    }

    @RequestMapping(value = "/httpLogs")
    public ThinkerResponse httpLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/redisLogs");

            thinkerTable.column("content", "显示内容");

        }).page();
    }

    @RequestMapping(value = "/sysLogs")
    public ThinkerResponse sysLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs?is_user=1");

            thinkerTable.search(thinkerForm -> {
                thinkerForm.input("content", "内容搜索");
            });

            thinkerTable.column("userId", "用户ID").setWidth("100px");
            thinkerTable.column("userType", "用户类型").setWidth("80px");
            thinkerTable.column("stages", "运行方法");
            thinkerTable.column("typeName", "归属类型");
            thinkerTable.column("content", "提交内容");
            thinkerTable.column("message", "输出数据");
            thinkerTable.column("createTime", "记录时间").setWidth("160px");

        }).page();
    }

    @RequestMapping(value = "/fileLogs")
    public ThinkerResponse fileLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/fileLogs");

            thinkerTable.column("text", "文件名称");
            thinkerTable.column("value", "文件路径");
            thinkerTable.column("op", "操作").setWidth("80px").button(button -> {
                button.setLabel("下载").setType("primary").setSize("small")
                        .setOnClick("() => {" +
                                "   window.open('/thinker/admin/downloadLogs?path='+slotData.row.text+'&"+ThinkerAdmin.properties().getToken().getTokenKey()+"='+_$store.state.user.token);" +
                                "}");
            });

        }).page();
    }

    @RequestMapping(value = "/server")
    public ThinkerResponse server() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/server");

            thinkerTable.column("label", "参数名称");
            thinkerTable.column("value", "参数值");

            thinkerTable.getPagerConfig().setPageSize(1000);

        }).page();
    }

    @RequestMapping(value = "/generateSql")
    public ThinkerResponse generateSql() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/fetchTables");

            thinkerTable.search(thinkerForm -> {
                List<LabelValue> labelValues = new ArrayList<>();
                try{
                    String[] strings = ((DynamicRoutingDataSource) SpringContext.getBean(DynamicDataSourceAutoConfiguration.class).dataSource()).getDataSources().keySet().toArray(new String[]{});
                    for (int i = 0; i < strings.length; i++) {
                        labelValues.add(LabelValue.create(strings[i], strings[i]));
                    }
                } catch (Exception ignored) {

                }
                thinkerForm.select("datasourceName", "数据源名称").setOptions(labelValues);
                thinkerForm.input("packageName", "生成包名称");
            });

            thinkerTable.checkbox().setWidth("60%").setField("label").setTitle("表名称");
            thinkerTable.column("value", "表描述");

            thinkerTable.toolbar().button(elButton -> {
                elButton.setLabel("生成").setType("success").setIcon("Download")
                        .setOnClick("(e) => {" +
                                        "   let generateTables = [];" +
                                        "   slotData.$table.getCheckboxRecords(true).forEach(item => {" +
                                        "       generateTables.push(item.label);" +
                                        "   });" +
                                        "   window.open('/restful/thinker/monitor/systemLogs/genderateTables?tableNames='+generateTables.join(',')+'" +
                                        "&datasourceName='+slotData.$grid.reactData.formData.datasourceName+'" +
                                        "&packageName='+slotData.$grid.reactData.formData.packageName+'" +
                                        "&"+ThinkerAdmin.properties().getToken().getTokenKey()+"='+_$store.state.user.token)" +
                                        "}"
                        );
            });

            thinkerTable.getPagerConfig().setPageSize(1000);
        }).page();
    }
}
