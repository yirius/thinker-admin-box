package com.thinker.framework.framework.database.services;

import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public abstract class BasePageServices<T> {
    protected ThinkerServiceImpl<?, T> thinkerService = null;

    protected ThinkerWrapper<T> thinkerWrapper = null;

    public BasePageServices(ThinkerServiceImpl<?, T> service) {
        this.thinkerService = service;
        this.setThinkerWrapper(new ThinkerWrapper<>());
    }

    public abstract PageServiceResult<T> getResult();
}
