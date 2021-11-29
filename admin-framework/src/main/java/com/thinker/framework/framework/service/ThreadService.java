package com.thinker.framework.framework.service;

import cn.hutool.core.lang.Dict;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class ThreadService {
    /** 私有方法，获取存储参数 **/
    private Dict getThread() {
        Dict dict = (Dict) RequestContextHolder.currentRequestAttributes().getAttribute("THINKER_THREAD_DICT", RequestAttributes.SCOPE_REQUEST);
        if(dict == null) {
            dict = Dict.create();
            RequestContextHolder.currentRequestAttributes().setAttribute("THINKER_THREAD_DICT", dict, RequestAttributes.SCOPE_REQUEST);
        }
        return dict;
    }

    public void setObject(String name, Object value) {
        getThread().set(name, value);
    }

    public Object getObject(String name) {
        return getThread().get(name);
    }

    public String getString(String name, String defaultValue) {
        return getThread().containsKey(name) ? getThread().getStr(name) : defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        return getThread().containsKey(name) ? getThread().getInt(name) : defaultValue;
    }

    public void clear() {
        getThread().clear();
    }
}
