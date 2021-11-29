package com.thinker.framework.framework.support.driver;

import cn.hutool.crypto.SecureUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class CacheDriver {

    protected String prefix = "THINKER_";

    abstract public Boolean has(String key);

    abstract public Object get(String key);

    abstract public Object get(String key, Object defaultValue);

    abstract public Boolean set(String key, Object value);

    abstract public Boolean set(String key, Object value, String tagName);

    abstract public Boolean set(String key, Object value, Long time);

    abstract public Boolean set(String key, Object value, Long time, String tagName);

    abstract public Long inc(String key);

    abstract public Long inc(String key, Long delta);

    abstract public Long dec(String key);

    abstract public Long dec(String key, Long delta);

    abstract public void rm(String key);

    abstract public void clear(String tagName);

    /**
     * 设置标签类
     * @param tagName
     * @param name
     */
    protected void setTagItem(String tagName, String name){
        List<String> value = this.getTagItem(tagName);
        if(!value.contains(name)){
            value.add(name);
            this.set(this.getTagKey(tagName), value);
        }
    }

    /**
     * 获取标签中存在多少个内容
     * @param tagName
     * @return
     */
    protected List<String> getTagItem(String tagName){
        tagName = this.getTagKey(tagName);
        if(this.has(tagName)){
            return (List<String>) this.get(tagName);
        }else{
            return new ArrayList<>();
        }
    }

    protected String getCacheKey(String key){
        return prefix + key;
    }

    protected String getTagKey(String tag){
        return "TAG_" + SecureUtil.md5(tag).toUpperCase();
    }

}
