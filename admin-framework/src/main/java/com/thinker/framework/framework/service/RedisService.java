package com.thinker.framework.framework.service;

import com.thinker.framework.framework.support.driver.CacheDriver;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService extends CacheDriver {

    /**
     * 初始化redis操作实例
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean has(String key) {
        try {
            return redisTemplate.hasKey(this.getCacheKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(this.getCacheKey(key));
    }

    @Override
    public Object get(String key, Object defaultValue) {
        Object value = this.get(key);
        return value == null ? defaultValue : value;
    }

    @Override
    public Boolean set(String key, Object value) {
        return this.set(key, value, 0L);
    }

    @Override
    public Boolean set(String key, Object value, String tagName) {
        return this.set(key, value, 0L, tagName);
    }

    @Override
    public Boolean set(String key, Object value, Long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(this.getCacheKey(key), value, time, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(this.getCacheKey(key), value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean set(String key, Object value, Long time, String tagName) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(this.getCacheKey(key), value, time, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(this.getCacheKey(key), value);
            }
            if(tagName != null){
                this.setTagItem(tagName, key);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long inc(String key) {
        return this.inc(key, 1L);
    }

    @Override
    public Long inc(String key, Long delta) {
        if (delta < 0) {
            throw new ThinkerException("message.thinker.exceptions.incFactorZero", "递增因子必须大于0", 0);
        }
        return redisTemplate.opsForValue().increment(this.getCacheKey(key), delta);
    }

    @Override
    public Long dec(String key) {
        return this.dec(key, 1L);
    }

    @Override
    public Long dec(String key, Long delta) {
        if (delta < 0) {
            throw new ThinkerException("message.thinker.exceptions.decFactorZero", "递减因子必须大于0", 0);
        }
        return redisTemplate.opsForValue().decrement(this.getCacheKey(key), delta);
    }

    @Override
    public void rm(String key) {
        if (key != null) {
            redisTemplate.delete(this.getCacheKey(key));
        }
    }

    @Override
    public void clear(String tagName) {
        List<String> names = this.getTagItem(tagName);
        names.forEach(this::rm);

        this.rm(this.getTagKey(tagName));
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return Boolean
     */
    public Boolean expire(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(this.getCacheKey(key), time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet
     *
     * @param key  键 不能为 null
     * @param item 项 不能为 null
     * @return 值
     */
    public Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(this.getCacheKey(key), item);
    }

    /**
     * 获取 hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hashMapGet(String key) {
        return redisTemplate.opsForHash().entries(this.getCacheKey(key));
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public Boolean hashMapSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(this.getCacheKey(key), map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public Boolean hashMapSet(String key, Map<String, Object> map, Long time) {
        try {
            redisTemplate.opsForHash().putAll(this.getCacheKey(key), map);
            if (time > 0) {
                expire(this.getCacheKey(key), time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hashSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(this.getCacheKey(key), item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hashSet(String key, String item, Object value, Long time) {
        try {
            redisTemplate.opsForHash().put(this.getCacheKey(key), item, value);
            if (time > 0) {
                expire(this.getCacheKey(key), time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为 null
     * @param item 项 可以使多个不能为 null
     */
    public void hashDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(this.getCacheKey(key), item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为 null
     * @param item 项 不能为 null
     * @return true 存在 false不存在
     */
    public Boolean hashHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(this.getCacheKey(key), item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return Double
     */
    public Double hashInc(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(this.getCacheKey(key), item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return Double
     */
    public Double hashDec(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(this.getCacheKey(key), item, -by);
    }

    /**
     * 根据 key获取 Set中的所有值
     *
     * @param key 键
     * @return Set
     */
    public Set<Object> setGet(String key) {
        try {
            return redisTemplate.opsForSet().members(this.getCacheKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean setHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(this.getCacheKey(key), value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long setSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(this.getCacheKey(key), values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long setSet(String key, Long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(this.getCacheKey(key), values);
            if (time > 0)
                expire(this.getCacheKey(key), time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return Long
     */
    public Long setGetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(this.getCacheKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(this.getCacheKey(key), values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return List
     */
    public List<?> listGet(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(this.getCacheKey(key), start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return Long
     */
    public Long listGetSize(String key) {
        try {
            return redisTemplate.opsForList().size(this.getCacheKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；
     *              index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return Object
     */
    public Object listGetIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(this.getCacheKey(key), index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    public Boolean listSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(this.getCacheKey(key), value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return Boolean
     */
    public Boolean listSet(String key, Object value, Long time) {
        try {
            redisTemplate.opsForList().rightPush(this.getCacheKey(key), value);
            if (time > 0)
                expire(this.getCacheKey(key), time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    public Boolean listSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(this.getCacheKey(key), value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return Boolean
     */
    public Boolean listSet(String key, List<Object> value, Long time) {
        try {
            redisTemplate.opsForList().rightPushAll(this.getCacheKey(key), value);
            if (time > 0)
                expire(this.getCacheKey(key), time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return Boolean
     */
    public Boolean listUpdateIndex(String key, Long index, Object value) {
        try {
            redisTemplate.opsForList().set(this.getCacheKey(key), index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long listRemove(String key, Long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(this.getCacheKey(key), count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
