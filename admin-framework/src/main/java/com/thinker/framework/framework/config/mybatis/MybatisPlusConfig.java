package com.thinker.framework.framework.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.thinker.framework.framework.database.utils.ShardingUtil;
import com.thinker.framework.framework.properties.ThinkerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @title MybatisPlusConfig
 * @description 便捷调用方法
 * @time 2020/2/25 12:46 下午
 * @return
 **/
@Slf4j
@Configuration
public class MybatisPlusConfig {

    @Autowired
    ThinkerProperties thinkerProperties;

    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            if(thinkerProperties.getSharding().containsKey(tableName)) {
                return parseTableName(sql, tableName, thinkerProperties.getSharding().get(tableName));
            }

            return tableName;
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

    /**
     * 解析表名
     * @param sql
     * @param tableName
     * @param dynamicType
     * @return
     */
    private String parseTableName(String sql, String tableName, String dynamicType) {
        String shardingStr = ShardingUtil.parseShardingStr(dynamicType, ShardingUtil.getSharding(tableName));
        // 检查表是否存在，不存在就创建
        ShardingUtil.checkShardingTable(tableName, shardingStr);
        //需要判断一下参数是否存在
        return tableName + "_" + shardingStr;
    }
}
