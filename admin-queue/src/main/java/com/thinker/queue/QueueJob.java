package com.thinker.queue;

import cn.hutool.core.util.ClassLoaderUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.SpringContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @title QueueJob
 * @description 便捷调用方法
 * @time 2020/2/26 10:38 下午
 * @return
 **/
@Data
@Slf4j
public class QueueJob {

    //当前使用的交换机
    private String exchange;

    //当前使用的队列
    private String queue;

    //指定执行的class
    private String runClass;

    //执行的数据
    private Object data;

    //已经执行了几次
    private Integer retry = 1;

    //是否删除，如果不删除根据默认时间继续执行
    private Boolean delete = false;

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMapData() {
        return (Map<String, Object>) data;
    }

    /**
     * @title invokeRun
     * @description 执行该job
     * @author YangYuanCe
     * @return
     **/
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void invokeRun() throws IllegalAccessException, InstantiationException {
        if(ClassLoaderUtil.isPresent(runClass)) {
            Class clazz = ClassLoaderUtil.loadClass(runClass);
            this.runClass(clazz);
        } else {
            log.error("run class not found: " + runClass);
        }
    }

    //运行
    private void runClass(Class<? extends QueueJobAbstract> queueJobAbstract) throws IllegalAccessException, InstantiationException {
        queueJobAbstract.newInstance().run(this);
    }

    /**
     * @title invokeError
     * @description 通知订单已经错误了，不再执行
     * @author YangYuanCe
     * @return
     **/
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void invokeError() throws IllegalAccessException, InstantiationException {
        if(ClassLoaderUtil.isPresent(runClass)) {
            Class clazz = ClassLoaderUtil.loadClass(runClass);
            this.errorClass(clazz);
        } else {
            log.error("run class not found: " + runClass);
        }
    }

    //运行
    private void errorClass(Class<? extends QueueJobAbstract> queueJobAbstract) throws IllegalAccessException, InstantiationException {
        queueJobAbstract.newInstance().error(this);
    }

    /**
     * @title delay
     * @description 订单延后再次执行
     * @author YangYuanCe
     * @param seconds
     * @return
     **/
    public void delay(Integer seconds) {
        retry = retry + 1;
        //还没到错误的时候，可以尝试
        if(retry <= ThinkerAdmin.properties().getConfig().getRabbitRetry()) {
            this.delete();
            SpringContext.getBean(QueueService.class).send(exchange, queue, this, seconds);
        }
    }

    /**
     * @title delete
     * @description 删除的的方法
     * @author YangYuanCe
     * @return
     **/
    public void delete() {
        this.delete = true;
    }
}
