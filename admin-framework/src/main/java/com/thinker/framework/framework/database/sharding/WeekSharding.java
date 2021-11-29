package com.thinker.framework.framework.database.sharding;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;

import java.util.Date;

public class WeekSharding implements ShardingTypes{
    @Override
    public String parseShardingSuffix(String shardingStr) {
        Date date = DateUtil.date();
        if(Validator.isNotEmpty(shardingStr)) {
            if(shardingStr.length() == 5) {
                return shardingStr;
            } else {
                date = DateUtil.parse(shardingStr,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyy-MM-dd HH:mm",
                        "yyyy-MM-dd HH",
                        "yyyy-MM-dd",
                        "yyyyMMdd",
                        "yy-MM-dd",
                        "yyMMdd"
                );
            }
        }
        return DateUtil.format(date, "yyMM") + DateUtil.weekOfMonth(date);
    }
}
