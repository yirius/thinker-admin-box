package com.thinker.framework.framework.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.serviceimpl.TkLogsImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GenerateSqlUtil {

    public static String getDatabaseName(String dataSourceName) {
        if(Validator.isEmpty(dataSourceName)) {
            dataSourceName = SpringContext.getBean(Environment.class).getProperty("spring.datasource.dynamic.primary");
        }
        String connectPath = SpringContext.getBean(Environment.class).getProperty("spring.datasource.dynamic.datasource."+dataSourceName+".url");
        if(connectPath != null) {
            String[] pathNames = connectPath.split("\\?")[0].split("/");
            return pathNames[pathNames.length - 1];
        }

        throw new ThinkerException("datasource数据源未找到");
    }

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getTableNames(String dataSourceName) {
        List<Map<String, Object>> tableNames = (List<Map<String, Object>>) SpringContext.getBean(TkLogsImpl.class).thinkerQuery(
                "select table_name tableName,table_comment description " +
                        "from information_schema.tables where table_schema = \"" + getDatabaseName(dataSourceName) + "\"");

        if(tableNames.size() > 0) {
            List<LabelValue> labelValues = new ArrayList<>();
            tableNames.forEach(map -> {
                String tableName = String.valueOf(map.get("tableName"));
                if(tableName != null && !tableName.contains("tkadmin_")) {
                    labelValues.add(LabelValue.create(tableName, map.get("description")).set("id", tableName));
                }
            });

            return labelValues;
        }

        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<String> generateTableFiles(String dataSourceName, String packageName, String tableName) {
        if(Validator.isEmpty(dataSourceName)) dataSourceName = "base";
        if(Validator.isEmpty(packageName)) packageName = "___PACKAGE_NAME___";

        List<String> pathList = new ArrayList<>();
        if(Validator.isNotEmpty(tableName) && !tableName.contains("tkadmin_")) {
            List<Map<String, Object>> tableColumns = (List<Map<String, Object>>) SpringContext.getBean(TkLogsImpl.class).thinkerQuery(
                    "select column_name as name,column_comment as descInfo,column_type as type" +
                            " from information_schema.columns where table_schema=\""+getDatabaseName(dataSourceName)+
                            "\" and table_name= \""+tableName+"\"");

            String className = StrUtil.toCamelCase(tableName).replace(tableName.split("_")[0], "");
            pathList.add(generateController(packageName, className, tableColumns));
            pathList.add(generateEntity(packageName, tableName, className, tableColumns));
            pathList.add(generateMapper(packageName, className));
            pathList.add(generateService(packageName, className));
            pathList.add(generateServiceImpl(packageName, className));
            pathList.add(generateRestful(packageName, className));
        }
        return pathList;
    }

    private static final Map<String, String> fieldToType = new HashMap<String, String>() {
        {
            put("varchar", "String");put("longtext", "String");put("text", "String");
            put("datetime", "DateTime");put("date", "Date");put("time", "Time");
            put("tinyint", "Integer");put("int", "Long");put("decimal", "BigDecimal");
        }
    };

    /**
     * 生成controller
     * @param packageName
     * @param className
     * @param tableColumns
     * @return
     */
    public static String generateController(String packageName, String className, List<Map<String, Object>> tableColumns) {
        String filePath = ThinkerAdmin.file().getDirPath("generateSql/controller/") + className + "Controller.java";
        FileUtil.writeString(thinkerrender.generates.controller.template(
                packageName, className,
                tableColumns.stream().peek(map -> {
                    map.put("name", StrUtil.toCamelCase(String.valueOf(map.getOrDefault("name", ""))));
                    String[] descInfo = String.valueOf(map.getOrDefault("descInfo", "")).split("\\|");
                    if(descInfo.length>2 && (descInfo[0].equals("1") || descInfo[0].equals("0"))) {
                        map.put("fieldName", descInfo[1]);
                    } else {
                        map.put("fieldName", StrUtil.join("|", descInfo));
                    }
                }).collect(Collectors.toList())
        ).render().toString(), filePath, StandardCharsets.UTF_8);
        return filePath;
    }

    /**
     * 生成Entity
     * @param packageName
     * @param tableName
     * @param className
     * @param tableColumns
     * @return
     */
    public static String generateEntity(String packageName, String tableName, String className, List<Map<String, Object>> tableColumns) {
        String filePath = ThinkerAdmin.file().getDirPath("generateSql/entity/") + className + ".java";
        FileUtil.writeString(thinkerrender.generates.entity.template(
                packageName, tableName, className,
                tableColumns.stream().peek(map -> {
                    map.put("name", StrUtil.toCamelCase(String.valueOf(map.getOrDefault("name", ""))));
                    // 判断生成类型
                    String type = String.valueOf(map.getOrDefault("type", "")).split("\\(")[0].toLowerCase();
                    map.put("fieldType", fieldToType.getOrDefault(type, "String"));
                    // 判断是否存在@Not系列
                    String[] descInfo = String.valueOf(map.getOrDefault("descInfo", "")).split("\\|");
                    if(descInfo.length>2 && descInfo[0].equals("1")) {
                        String notShowMessage = descInfo[2].contains("{") ? descInfo[2].replace("{field}", descInfo[1]) : descInfo[1] + descInfo[2];
                        map.put("descStr", "\n    " + (descInfo.length>3?"//"+descInfo[4]+"\n":"") +
                                "@Not"+(type.contains("int")||type.equals("decimal") ? "Null(message = \"":"Blank(message = \"") + notShowMessage + "\")");
                    }
                    if(!map.containsKey("descStr") && Validator.isNotEmpty(descInfo[0])) {
                        map.put("descStr", "//"+StrUtil.join("|", (Object) descInfo));
                    }
                }).collect(Collectors.toList())
        ).render().toString(), filePath, StandardCharsets.UTF_8);
        return filePath;
    }

    /**
     * 生成mapper
     * @param packageName
     * @param className
     * @return
     */
    public static String generateMapper(String packageName, String className) {
        String filePath = ThinkerAdmin.file().getDirPath("generateSql/mapper/") + className + "Mapper.java";
        FileUtil.writeString(thinkerrender.generates.mapper.template(
                packageName, className
        ).render().toString(), filePath, StandardCharsets.UTF_8);
        return filePath;
    }

    public static String generateService(String packageName, String className) {
        String filePath = ThinkerAdmin.file().getDirPath("generateSql/service/") + "I" + className + "Service.java";
        FileUtil.writeString(thinkerrender.generates.service.template(
                packageName, className
        ).render().toString(), filePath, StandardCharsets.UTF_8);
        return filePath;
    }

    public static String generateServiceImpl(String packageName, String className) {
        String filePath = ThinkerAdmin.file().getDirPath("generateSql/serviceimpl/") + className + "Impl.java";
        FileUtil.writeString(thinkerrender.generates.serviceimpl.template(
                packageName, className
        ).render().toString(), filePath, StandardCharsets.UTF_8);
        return filePath;
    }

    public static String generateRestful(String packageName, String className) {
        String filePath = ThinkerAdmin.file().getDirPath("generateSql/restful/") + className + "Restful.java";
        FileUtil.writeString(thinkerrender.generates.restful.template(
                packageName, className
        ).render().toString(), filePath, StandardCharsets.UTF_8);
        return filePath;
    }
}
