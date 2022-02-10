package com.thinker.framework.framework.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class ThreadService {
    /** 私有方法，获取存储参数 **/
    private Dict getThread() {
        if(RequestContextHolder.getRequestAttributes() == null) {
            String dictStr = ThreadContext.get("THINKER_THREAD_DICT");
            Dict dict = Dict.create();
            if(dictStr != null && Validator.isNotEmpty(dictStr)) {
                dict.putAll(JSON.parseObject(dictStr));
            }
            return dict;
        } else {
            Dict dict = (Dict) RequestContextHolder.currentRequestAttributes().getAttribute("THINKER_THREAD_DICT", RequestAttributes.SCOPE_REQUEST);
            if(dict == null) {
                dict = Dict.create();
                RequestContextHolder.currentRequestAttributes().setAttribute("THINKER_THREAD_DICT", dict, RequestAttributes.SCOPE_REQUEST);
            }
            return dict;
        }
    }

    public void setObject(String name, Object value) {
        if(RequestContextHolder.getRequestAttributes() == null) {
            ThreadContext.put("THINKER_THREAD_DICT", JSON.toJSONString(getThread().set(name, value)));
        } else {
            getThread().set(name, value);
        }
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
