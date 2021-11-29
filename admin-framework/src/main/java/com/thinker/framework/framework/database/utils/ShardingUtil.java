package com.thinker.framework.framework.database.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ClassLoaderUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import com.thinker.framework.framework.database.sharding.*;
import com.thinker.framework.framework.support.SpringContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ShardingUtil {

    private static final List<String> shardingTableNames = new ArrayList<>();

    /**
     * 设置分表后缀
     * @param entity
     * @param sharding
     */
    public static void setSharding(Class<?> entity, String sharding) {
        ThinkerAdmin.thread().setObject("SHARD_"+DatabaseUtil.getTableName(entity), sharding);
    }

    public static String getSharding(Class<?> entity) {
        return getSharding(DatabaseUtil.getTableName(entity));
    }

    public static String getSharding(String tableName) {
        return ThinkerAdmin.thread().getString("SHARD_"+tableName, "");
    }

    /**
     * 使用不同类型，来获取分表名称
     * @param dynamicType
     * @return
     */
    // 记录一下预定义的种类
    private static final Map<String, ShardingTypes> shardingTypes = new HashMap<String, ShardingTypes>() {{
        put("day", new DaySharding());
        put("week", new WeekSharding());
        put("month", new MonthSharding());
        put("year", new YearSharding());
    }};

    public static String parseShardingStr(String dynamicType) {
        return parseShardingStr(dynamicType, null);
    }

    public static String parseShardingStr(String dynamicType, String shardingStr) {
        if(!shardingTypes.containsKey(dynamicType)) {
            try{
                shardingTypes.put(dynamicType, (ShardingTypes) ClassLoaderUtil.loadClass(dynamicType).newInstance());
            } catch (Exception err) {
                err.printStackTrace();
                dynamicType = "day";
            }
        }
        return shardingTypes.get(dynamicType).parseShardingSuffix(shardingStr);
    }

    /**
     * 检查是否存在分表字段, 一般是从setSharding设置的
     * @param entity
     */
    public static String checkShardingTableAndGetName(Class<?> entity) {
        return checkShardingTableAndGetName(SpringContext.getOneBeanUseType(ThinkerMapper.class), DatabaseUtil.getTableName(entity));
    }

    public static String checkShardingTableAndGetName(ThinkerMapper<?> thinkerMapper, String tableName) {
        String shardTableStr = getSharding(tableName);
        if(Validator.isNotEmpty(shardTableStr)) {
            if(ThinkerAdmin.properties().getSharding().containsKey(tableName)) {
                shardTableStr = parseShardingStr(ThinkerAdmin.properties().getSharding().get(tableName), shardTableStr);
            }
            checkShardingTable(thinkerMapper, tableName, shardTableStr);
        }
        return tableName + (Validator.isNotEmpty(shardTableStr) ? "_" + shardTableStr : "");
    }

    /**
     * 检查表是否存在, 部分地方可以传递ThinkerMapper，那就传递，否则直接读取
     * @param tableName
     * @param shardingStr
     */
    public static void checkShardingTable(String tableName, String shardingStr) {
        checkShardingTable(SpringContext.getOneBeanUseType(ThinkerMapper.class), tableName, shardingStr);
    }

    public static void checkShardingTable(ThinkerMapper<?> thinkerMapper, String tableName, String shardingStr) {
        String key = "TPL_" + tableName.toUpperCase() + "_" + shardingStr.toUpperCase();
        //判断是否存在cache
        if(!shardingTableNames.contains(key)) {
            ThinkerMapper.ThinkerQueryParam thinkerQueryParam = new ThinkerMapper.ThinkerQueryParam();
            thinkerQueryParam.setSql("SHOW TABLES LIKE '"+tableName+"_"+shardingStr+"'");
            //当前表并不存在表单，先查询
            List<?> shardingTable = thinkerMapper.thinkerquery(thinkerQueryParam);
            if(shardingTable.size() > 0) {
                //已经存在了，只是没记录
                shardingTableNames.add(key);
            } else {
                try{
                    thinkerQueryParam.setSql("CREATE TABLE `"+tableName+"_"+shardingStr+"` LIKE `"+tableName+"_template`");
                    thinkerMapper.thinkerquery(thinkerQueryParam);
                    shardingTableNames.add(key);
                }catch (Exception e) {
                    if(e.getMessage().contains("already exists")) {
                        shardingTableNames.add(key);
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
