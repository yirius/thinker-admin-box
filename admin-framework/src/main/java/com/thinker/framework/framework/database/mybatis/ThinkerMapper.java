package com.thinker.framework.framework.database.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinker.framework.framework.database.wrapper.ThinkerQueryChainWrapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ThinkerMapper<T> extends BaseMapper<T> {

    @Select({"<script>",
            //从指定位置找到表
            "SELECT ${ew.sqlSelect} FROM ${ew.sqlTableName} ${ew.sqlTableAlias}",
            //判断连接参数
            " ${ew.sqlJoinStr} ",
            //判断其他查找参数
            "${ew.customSqlSegment}",
            //判断limit等参数
            " ${ew.pageLimitStr} ",
            "</script> "})
    List<Map<String, Object>> thinkersql(@Param("ew") Wrapper<T> query);

    @Update({"<script>",
            //从指定位置找到表
            "UPDATE ${ew.sqlTableName} SET ",
            //判断limit等参数
            " ${ew.fieldUpdateStr} ",
            //判断其他查找参数
            "${ew.customSqlSegment}",
            "</script> "})
    Integer thinkerupdate(@Param("ew") Wrapper<T> query);

    @Select({"<script>",
            //注入query的sql
            " ${tqp.sql} ",
            "</script> "})
    List<Map<String, Object>> thinkerquery(@Param("tqp") ThinkerQueryParam thinkerQueryParam);

    @Getter
    @Setter
    public class ThinkerQueryParam {
        private String sql;
    }
}
