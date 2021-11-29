package com.thinker.framework.framework.database.wrapper;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import com.thinker.framework.framework.database.utils.DatabaseUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Setter
@Getter
@Accessors(chain = true)
public class ThinkerQueryChainWrapper<T> extends QueryChainWrapper<T> {

    private Class<?> clazz;

    public ThinkerQueryChainWrapper(BaseMapper<T> baseMapper, Class<?> clazz) {
        super(baseMapper);
        super.wrapperChildren = new ThinkerWrapper<>();
        this.clazz = clazz;
    }

    /** 进行多表快速查询 **/
    public ThinkerQueryChainWrapper<T> with(String ...withNames) {
        ((ThinkerWrapper<T>) this.wrapperChildren).with(withNames);
        return (ThinkerQueryChainWrapper<T>) this.typedThis;
    }

    /**
     * 重写LIST方法，加入FILL(JOIN和填充)判断
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> list() {
        if(((ThinkerWrapper<T>)this.wrapperChildren).getWiths().size() > 0) {
            // 检查一下字段
            DatabaseUtil.checkWrapperFields(clazz, (ThinkerWrapper<T>) this.wrapperChildren, (ThinkerMapper<T>) this.getBaseMapper());

            return (List<T>) JSON.parseArray(JSON.toJSONString(
                    DatabaseUtil.injectJoinFillResult(
                            clazz,
                            (ThinkerMapper<T>) this.getBaseMapper(),
                            (ThinkerWrapper<T>) this.wrapperChildren
                    )
            ), clazz);
        } else {
            return super.list();
        }
    }

    /**
     * 重写ONE方法，加入FILL(JOIN和填充)判断
     * @return
     */
    public T one() {
        if(((ThinkerWrapper<T>)this.wrapperChildren).getWiths().size() > 0) {
            List<T> tList = this.list();
            return tList.size() == 0 ? null : tList.get(0);
        } else {
            return super.one();
        }
    }
}
