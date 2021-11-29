package com.thinker.framework.framework.database.sharding;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;

import java.util.Date;

public class MonthSharding implements ShardingTypes{
    @Override
    public String parseShardingSuffix(String shardingStr) {
        Date date = DateUtil.date();
        if(Validator.isNotEmpty(shardingStr)) {
            date = DateUtil.parse(shardingStr,
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm",
                    "yyyy-MM-dd HH",
                    "yyyy-MM-dd",
                    "yyyyMMdd",
                    "yy-MM-dd",
                    "yyyyMM",
                    "yyMMdd",
                    "yyMM"
            );
        }
        return DateUtil.format(date, "yyMM");
    }
}
