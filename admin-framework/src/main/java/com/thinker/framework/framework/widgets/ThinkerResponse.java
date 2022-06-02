package com.thinker.framework.framework.widgets;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class ThinkerResponse extends HashMap<String, Object> {

    public ThinkerResponse code(Integer code) {
        this.put("code", code);
        return this;
    }

    public ThinkerResponse msg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public ThinkerResponse msg(String msg, Object... objs) {
        this.put("msg", StrUtil.format(msg, objs));
        return this;
    }

    /**
     * 填充对应参数，前端可以读取对应的i18n
     * @param local
     * @return
     */
    public ThinkerResponse local(String local) {
        this.put("_local", local);
        return this;
    }

    public ThinkerResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    // 复写一下链表操作
    public ThinkerResponse set(String name, Object value) {
        this.put(name, value);
        return this;
    }

    public ThinkerResponse success() {
        this.code(1);
        this.putIfAbsent("data", new JSONObject());
        this.putIfAbsent("msg", "OK");
        return this;
    }

    public ThinkerResponse fail() {
        this.code(0);
        this.putIfAbsent("data", new JSONObject());
        this.putIfAbsent("msg", "FAIL");
        return this;
    }
}
