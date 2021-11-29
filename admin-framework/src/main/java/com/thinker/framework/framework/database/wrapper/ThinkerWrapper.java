package com.thinker.framework.framework.database.wrapper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Setter
@Getter
@Accessors(chain = true)
public class ThinkerWrapper<T> extends QueryWrapper<T> {

    /** 名称及别名 **/
    private String sqlTableName = "";

    public ThinkerWrapper<T> tablename(String sqlTableName) {
        return this.setSqlTableName(sqlTableName);
    }

    private String sqlTableAlias = "";

    public ThinkerWrapper<T> alias(String sqlTableAlias) {
        return this.setSqlTableAlias(sqlTableAlias);
    }

    /** 连表查询 **/
    private final List<String> sqlJoin = new ArrayList<>();

    public String getSqlJoinStr() {
        return StrUtil.join(" ", this.sqlJoin);
    }

    public ThinkerWrapper<T> join(String joinTable, String onExpRaw, String joinType) {
        if(!this.sqlJoin.contains(joinType.toUpperCase() + " JOIN " + joinTable + " ON " + onExpRaw)) {
            this.sqlJoin.add(joinType.toUpperCase() + " JOIN " + joinTable + " ON " + onExpRaw);
        }
        return (ThinkerWrapper<T>) this.typedThis;
    }

    public ThinkerWrapper<T> join(String joinTable, String onExpRaw) {
        return this.join(joinTable, onExpRaw, "INNER");
    }

    public ThinkerWrapper<T> leftJoin(String joinTable, String onExpRaw) {
        return this.join(joinTable, onExpRaw, "LEFT");
    }

    public ThinkerWrapper<T> rightJoin(String joinTable, String onExpRaw) {
        return this.join(joinTable, onExpRaw, "RIGHT");
    }

    /** 分页查询 **/
    private int page = 0;
    private int limit = 10;

    public String getPageLimitStr() {
        if(this.page > 0) {
            return "limit " + (this.page-1)*this.limit + "," + this.limit;
        }
        return "";
    }

    public ThinkerWrapper<T> page(int page) {
        this.page = Math.max(page, 1);
        return (ThinkerWrapper<T>) this.typedThis;
    }

    public ThinkerWrapper<T> limit(int limit) {
        this.limit = Math.max(limit, 1);
        return (ThinkerWrapper<T>) this.typedThis;
    }

    /** 递增或递减 **/
    private List<String> fieldUpdate = new ArrayList<>();

    public String getFieldUpdateStr() { return StrUtil.join(",", this.fieldUpdate); }

    public ThinkerWrapper<T> updateField(String field, Object value) {
        this.fieldUpdate.add("`"+field+"`='"+value+"'");
        return (ThinkerWrapper<T>) this.typedThis;
    }

    public ThinkerWrapper<T> inc(String field, int step) {
        this.fieldUpdate.add("`"+field+"`=`"+field+"`+"+step);
        return (ThinkerWrapper<T>) this.typedThis;
    }

    public ThinkerWrapper<T> inc(String field) {
        return inc(field, 1);
    }

    public ThinkerWrapper<T> dec(String field, int step) {
        this.fieldUpdate.add("`"+field+"`=`"+field+"`-"+step);
        return (ThinkerWrapper<T>) this.typedThis;
    }

    public ThinkerWrapper<T> dec(String field) {
        return dec(field, 1);
    }

    /** 进行多表快速查询 **/
    private List<String> withs = new ArrayList<>();

    public ThinkerWrapper<T> with(String ...withNames) {
        withs.addAll(Arrays.asList(withNames));
        return (ThinkerWrapper<T>) this.typedThis;
    }
}
