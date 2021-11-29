package com.thinker.framework.framework.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @title AutoFillTableConfig
 * @description 便捷调用方法
 * @time 2020/2/16 12:10 上午
 * @return
 **/
@Slf4j
@Component
public class MybatisAutoFillConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.getValue("createTime") == null) {
            if(metaObject.hasSetter("createTime")) {
                this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if(metaObject.getValue("updateTime") == null) {
            if(metaObject.hasSetter("updateTime")) {
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            }
        }
    }
}
