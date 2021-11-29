package com.thinker.framework.admin.controller;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.renders.PageParams;
import com.thinker.framework.framework.renders.enums.ButtonType;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.token.extend.ThinkerController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/thinker/monitor")
public class MonitorController extends ThinkerController {

    @RequestMapping(value = "/loginLogs.vue")
    public String loginLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs?is_null_user=1");

            thinkerTable.search(thinkerForm -> {
                thinkerForm.input("content", "内容搜索");
            });

            thinkerTable.column("content", "显示内容");
            thinkerTable.column("createTime", "记录时间").setWidth("140px");

            thinkerTable.toolbar().defaultTools();

        }).page().toString();
    }

    @RequestMapping(value = "/httpLogs.vue")
    public String httpLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/redisLogs");

            thinkerTable.column("content", "显示内容");

            thinkerTable.toolbar().defaultTools();

        }).page().toString();
    }

    @RequestMapping(value = "/sysLogs.vue")
    public String sysLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs?is_user=1");

            thinkerTable.search(thinkerForm -> {
                thinkerForm.input("content", "内容搜索");
            });

            thinkerTable.column("userId", "用户ID").setWidth("100px");
            thinkerTable.column("userType", "用户类型").setWidth("80px");
            thinkerTable.column("content", "显示内容");
            thinkerTable.column("createTime", "记录时间").setWidth("140px");

            thinkerTable.toolbar().defaultTools();

        }).page().toString();
    }

    @RequestMapping(value = "/fileLogs.vue")
    public String fileLogs() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/fileLogs");

            thinkerTable.column("text", "文件名称");
            thinkerTable.column("value", "文件路径");
            thinkerTable.column("op", "操作").setWidth("80px")
                    .button("download", "下载")
                    .columnButtonClick("window.location.href='/thinker/admin/downloadLogs?path='+scope.row.text+'&"+
                            ThinkerAdmin.properties().getToken().getTokenKey()+"='+this.$store.state.user.token");

            thinkerTable.toolbar().defaultTools();

        }).page().toString();
    }

    @RequestMapping(value = "/server.vue")
    public String server() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/server");

            thinkerTable.column("label", "参数名称");
            thinkerTable.column("value", "参数值");

            thinkerTable.toolbar().defaultTools();

        }).page().toString();
    }

    @RequestMapping(value = "/generateSql.vue")
    public String generateSql() {
        return ThinkerAdmin.table(thinkerTable -> {
            thinkerTable.setApi("/restful/thinker/monitor/systemLogs/fetchTables");

            thinkerTable.search(thinkerForm -> {

                PageParams.setSetupSuffixScript(thinkerForm.getLayoutId() + "_formValue.value.datasourceName = '"+
                        SpringContext.getBean(Environment.class).getProperty("spring.datasource.dynamic.primary")+"';");

                thinkerForm.input("datasourceName", "数据源名称");
                thinkerForm.input("packageName", "生成包名称");
            });

            thinkerTable.column("label", "表名称");
            thinkerTable.column("value", "表描述");

            thinkerTable.toolbar().defaultTools().button("generateSql", "生成").setType(ButtonType.SUCCESS).setIcon("Download")
                    .setClick("let generateTables = [];" +
                            "toRaw(unref(this."+thinkerTable.getLayoutId()+"_selectedData)).forEach(item => {" +
                            "   generateTables.push(item.id);" +
                            "});" +
                            "window.open('/restful/thinker/monitor/systemLogs/genderateTables?tableNames='+generateTables.join(',')+'" +
                            "&datasourceName='+this."+thinkerTable.getLayoutId()+"_formValue.datasourceName+'" +
                            "&packageName='+this."+thinkerTable.getLayoutId()+"_formValue.packageName+'" +
                            "&"+ThinkerAdmin.properties().getToken().getTokenKey()+"='+this.$store.state.user.token)");

            thinkerTable.setPageSizes(Collections.singletonList(1000)).getPage().setSize(1000);
        }).page().toString();
    }
}
