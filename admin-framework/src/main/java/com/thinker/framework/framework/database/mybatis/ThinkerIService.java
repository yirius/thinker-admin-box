package com.thinker.framework.framework.database.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.framework.framework.database.entity.ThinkerEntity;
import com.thinker.framework.framework.database.exceptions.LazyWithFillException;
import com.thinker.framework.framework.database.exceptions.UpdateException;
import com.thinker.framework.framework.database.services.pagelist.PageListService;
import com.thinker.framework.framework.database.wrapper.ThinkerQueryChainWrapper;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ThinkerIService<T> extends IService<T> {

    default ThinkerQueryChainWrapper<T> query() {
        return new ThinkerQueryChainWrapper<>(this.getBaseMapper(), this.getCurrentModel());
    }

    // 具体查询接口
    List<Map<String, Object>> thinkerSelect(ThinkerWrapper<T> thinkerWrapper);
    // 返回指定的Bean
    List<T> thinkerList(ThinkerWrapper<T> thinkerWrapper);
    // 统计接口
    Long thinkerCount(ThinkerWrapper<T> thinkerWrapper);
    // 更新接口
    Boolean thinkerUpdate(ThinkerWrapper<T> thinkerWrapper) throws UpdateException;
    // 更新接口
    Boolean thinkerUpdateBatch(Collection<T> entityList, Function<T, QueryWrapper<T>> queryWrapperFunction) throws UpdateException;
    // 直接执行
    Object thinkerQuery(String sql);
    // 返回当前model
    Class<T> getCurrentModel();
    // 注入参数
    <R extends ThinkerEntity> void injectWithFill(R entity, String ...withs) throws LazyWithFillException;
    // 按分页查询的接口
    PageListService<T> pageList();
}
