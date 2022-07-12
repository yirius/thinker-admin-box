package com.thinker.framework.framework.database.mybatis;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinker.framework.framework.database.entity.ThinkerEntity;
import com.thinker.framework.framework.database.exceptions.LazyWithFillException;
import com.thinker.framework.framework.database.exceptions.UpdateException;
import com.thinker.framework.framework.database.services.pagelist.PageListService;
import com.thinker.framework.framework.database.utils.DatabaseUtil;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
public class ThinkerServiceImpl<M extends ThinkerMapper<T>, T> extends ServiceImpl<M, T> implements ThinkerIService<T> {

    @Override
    public List<Map<String, Object>> thinkerSelect(ThinkerWrapper<T> thinkerWrapper) {
        // 检查一下字段
        DatabaseUtil.checkWrapperFields(getCurrentModel(), thinkerWrapper, this.getBaseMapper());

        return DatabaseUtil.injectJoinFillResult(getCurrentModel(), this.getBaseMapper(), thinkerWrapper);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> thinkerList(ThinkerWrapper<T> thinkerWrapper) {
        // 找到对应的Entity
        Class<T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        // 转化
        return JSON.parseArray(JSON.toJSONString(this.thinkerSelect(thinkerWrapper)), entityClass);
    }

    @Override
    public Long thinkerCount(ThinkerWrapper<T> thinkerWrapper) {
        // 检查一下字段
        DatabaseUtil.checkWrapperFields(getCurrentModel(), thinkerWrapper, this.getBaseMapper());

        // 需要最后重置一下查询数量
        thinkerWrapper.setPage(0);
        thinkerWrapper.select("count(*) as count");

        List<Map<String, Object>> mapList = this.baseMapper.thinkersql(thinkerWrapper);
        if(mapList.size() > 1) {
            return (long) mapList.size();
        } else {
            return (Long) this.baseMapper.thinkersql(thinkerWrapper).get(0).get("count");
        }
    }

    @Override
    public Boolean thinkerUpdate(ThinkerWrapper<T> thinkerWrapper) throws UpdateException {
        // 检查一下字段
        DatabaseUtil.checkWrapperFields(getCurrentModel(), thinkerWrapper, this.getBaseMapper());

        if(Validator.isEmpty(thinkerWrapper.getFieldUpdateStr())) {
            throw new UpdateException("message.thinker.exceptions.updateNoParam", "暂无更新参数", 450);
        }

        if(Validator.isEmpty(thinkerWrapper.getCustomSqlSegment())) {
            throw new UpdateException("message.thinker.exceptions.updateNoWhere", "暂无更新条件", 451);
        }

        return this.baseMapper.thinkerupdate(thinkerWrapper) > 0;
    }

    @Override
    public Object thinkerQuery(String sql) {
        ThinkerMapper.ThinkerQueryParam thinkerQueryParam = new ThinkerMapper.ThinkerQueryParam();
        thinkerQueryParam.setSql(sql);
        return this.baseMapper.thinkerquery(thinkerQueryParam);
    }

    @Override
    public Class<T> getCurrentModel() {
        return this.currentModelClass();
    }

    @Override
    public <R extends ThinkerEntity> void injectWithFill(R entity, String ...withs) throws LazyWithFillException {
        DatabaseUtil.injectWithResult(this.baseMapper, entity.getClass(), entity, true, withs);
    }

    @Override
    public PageListService<T> pageList() {
        return new PageListService<>(this);
    }
}
