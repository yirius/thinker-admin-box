package com.thinker.framework.admin.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.system.SystemUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkLogs;
import com.thinker.framework.admin.mapper.TkLogsMapper;
import com.thinker.framework.admin.serviceimpl.TkLogsImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.entity.vo.WrapperValue;
import com.thinker.framework.framework.utils.GenerateSqlUtil;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.extend.ThinkerRestful;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/restful/thinker/monitor/systemLogs")
public class TkSystemLogsRestful extends ThinkerRestful<TkLogsMapper, TkLogs> {
    public TkSystemLogsRestful() {
        setUseTable(TkLogsImpl.class);

        getUseWhere().add(new WrapperValue("is_null_user", "isNull", "user_id"));
        getUseWhere().add(new WrapperValue("is_user", "isNotNull", "user_id"));
        getUseWhere().add(new WrapperValue("content", "like"));
    }

    /**
     * Redis的日志读取
     * @return
     */
    @RequestMapping(value = "/redisLogs")
    public ThinkerResponse redisLogs() {
        String page = ThinkerAdmin.request().getRequestParam("page");
        long pageCount = Validator.isEmpty(page) ? 1 : Long.parseLong(page);
        String limit = ThinkerAdmin.request().getRequestParam("limit");
        long limitCount = Validator.isEmpty(limit) ? 100L : Long.parseLong(limit);

        List<String> stringList = (List<String>) ThinkerAdmin.redis().listGet("ADMIN_REDIS_LOGS", (pageCount-1)*limitCount, (pageCount)*limitCount);

        return new ThinkerResponse().data(
                Dict.create()
                        .set("items", stringList.stream().map(s -> Dict.create().set("content", s)))
                        .set("total", ThinkerAdmin.redis().listGetSize("ADMIN_REDIS_LOGS"))
        ).success();
    }

    /**
     * 文件日志的读取
     * @return
     */
    @RequestMapping(value = "/fileLogs")
    @SuppressWarnings("unchecked")
    public ThinkerResponse fileLogs() {
        List<TextValue> textValues = new ArrayList<>();
        TextValue logValues = ThinkerAdmin.file().getDirectory(ThinkerAdmin.file().getDirPath("logs/"));
        ((List<TextValue>) logValues.getObj("childs")).forEach(textValue -> {
            textValues.addAll((List<TextValue>) textValue.getObj("childs"));
        });

        return new ThinkerResponse().data(Dict.create().set("items", textValues).set("total", textValues.size())).success();
    }

    @RequestMapping(value = "/server")
    public ThinkerResponse server() {
        List<LabelValue> resultData = new ArrayList<>();
        resultData.add(LabelValue.create("java.version", SystemUtil.getJavaInfo().getVersion()));
        resultData.add(LabelValue.create("java.vendor", SystemUtil.getJavaInfo().getVendor()));

        resultData.add(LabelValue.create("javaruntime.classVersion", SystemUtil.getJavaRuntimeInfo().getClassVersion()));
        resultData.add(LabelValue.create("javaruntime.homeDir", SystemUtil.getJavaRuntimeInfo().getHomeDir()));
        resultData.add(LabelValue.create("javaruntime.name", SystemUtil.getJavaRuntimeInfo().getName()));
        resultData.add(LabelValue.create("javaruntime.sunArchDataModel", SystemUtil.getJavaRuntimeInfo().getSunArchDataModel()));
        resultData.add(LabelValue.create("javaruntime.version", SystemUtil.getJavaRuntimeInfo().getVersion()));

        resultData.add(LabelValue.create("jvm.version", SystemUtil.getJvmInfo().getVersion()));
        resultData.add(LabelValue.create("jvm.name", SystemUtil.getJvmInfo().getName()));
        resultData.add(LabelValue.create("jvm.vendor", SystemUtil.getJvmInfo().getVendor()));
        resultData.add(LabelValue.create("jvm.info", SystemUtil.getJvmInfo().getInfo()));

        resultData.add(LabelValue.create("host.name", SystemUtil.getHostInfo().getName()));
        resultData.add(LabelValue.create("host.address", SystemUtil.getHostInfo().getAddress()));

        resultData.add(LabelValue.create("os.arch", SystemUtil.getOsInfo().getArch()));
        resultData.add(LabelValue.create("os.name", SystemUtil.getOsInfo().getName()));
        resultData.add(LabelValue.create("os.version", SystemUtil.getOsInfo().getVersion()));
        resultData.add(LabelValue.create("os.fileSeparator", SystemUtil.getOsInfo().getFileSeparator()));
        resultData.add(LabelValue.create("os.lineSeparator", SystemUtil.getOsInfo().getLineSeparator()));
        resultData.add(LabelValue.create("os.pathSeparator", SystemUtil.getOsInfo().getPathSeparator()));

        resultData.add(LabelValue.create("user.country", SystemUtil.getUserInfo().getCountry()));
        resultData.add(LabelValue.create("user.language", SystemUtil.getUserInfo().getLanguage()));
        resultData.add(LabelValue.create("user.name", SystemUtil.getUserInfo().getName()));
        resultData.add(LabelValue.create("user.tempDir", SystemUtil.getUserInfo().getTempDir()));
        resultData.add(LabelValue.create("user.currentDir", SystemUtil.getUserInfo().getCurrentDir()));

        resultData.add(LabelValue.create("runtime.availableProcessors", SystemUtil.getRuntimeInfo().getRuntime().availableProcessors()));
        resultData.add(LabelValue.create("runtime.maxmemory", (SystemUtil.getRuntimeInfo().getMaxMemory() / (1024 * 1024)) + "MB"));
        resultData.add(LabelValue.create("runtime.totalmemory", (SystemUtil.getRuntimeInfo().getTotalMemory() / (1024 * 1024)) + "MB"));
        resultData.add(LabelValue.create("runtime.freememory", (SystemUtil.getRuntimeInfo().getFreeMemory() / (1024 * 1024)) + "MB"));
        resultData.add(LabelValue.create("runtime.usablememory", (SystemUtil.getRuntimeInfo().getUsableMemory() / (1024 * 1024)) + "MB"));

        return new ThinkerResponse().data(Dict.create().set("items", resultData).set("total", resultData.size())).success();
    }

    /**
     * 读取到当前datasource内有几个表
     * @param datasourceName
     * @return
     */
    @RequestMapping(value = "/fetchTables")
    public ThinkerResponse fetchTables(String datasourceName) {
        List<LabelValue> labelValues = GenerateSqlUtil.getTableNames(datasourceName);
        return new ThinkerResponse().data(Dict.create().set("items", labelValues).set("total", labelValues.size())).success();
    }

    @RequestMapping(value = "/genderateTables", produces = {"application/zip;charset=utf-8"})
    public byte[] genderateTables(String datasourceName, String packageName, String tableNames) {
        List<String> tableNameList = new ArrayList<>();
        if(Validator.isNotEmpty(tableNames)) Collections.addAll(tableNameList, tableNames.split(","));

        List<String> allTableNames = GenerateSqlUtil.getTableNames(datasourceName).stream()
                .map(labelValue -> labelValue.getStr("id")).collect(Collectors.toList());

        // 先删除文件
        String generateSqlPath = ThinkerAdmin.file().getDirPath("generateSql" + File.separator);
        FileUtil.del(generateSqlPath);

        // 然后生成打包
        allTableNames.forEach(s -> {
            if(tableNameList.size() == 0 || tableNameList.contains(s)) {
                GenerateSqlUtil.generateTableFiles(datasourceName, packageName, s);
            }
        });
        File zipFile = FileUtil.file(generateSqlPath + "download.zip");
        ZipUtil.zip(
                zipFile, true,
                FileUtil.file(generateSqlPath + "controller"),
                FileUtil.file(generateSqlPath + "entity"),
                FileUtil.file(generateSqlPath + "mapper"),
                FileUtil.file(generateSqlPath + "restful"),
                FileUtil.file(generateSqlPath + "service"),
                FileUtil.file(generateSqlPath + "serviceimpl")
        );

        return FileUtil.readBytes(zipFile);
    }
}
