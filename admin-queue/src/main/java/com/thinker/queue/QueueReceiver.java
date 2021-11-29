package com.thinker.queue;

import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.properties.ThinkerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @title QueueReceiver
 * @description 便捷调用方法
 * @time 2020/2/26 6:10 下午
 * @return
 **/
@Slf4j
@Configuration
public class QueueReceiver {

    @Autowired
    private ThinkerProperties thinkerProperties;

    private void _receive(String msg) {
        QueueJob queueJob = JSON.parseObject(msg, QueueJob.class);
        if(queueJob != null) {
            //重置删除字段
            queueJob.setDelete(false);

            try{
                //首先执行
                queueJob.invokeRun();
            }catch (Exception e) {
                e.printStackTrace();
            }

            //判断是否删除，如果不是删除，就会再次进入队列
            if(!queueJob.getDelete()) {
                //已经超过了最大重试次数
                if(queueJob.getRetry()+1 > thinkerProperties.getConfig().getRabbitRetry()) {
                    //执行错误方式
                    try{
                        queueJob.invokeError();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //重试延迟
                    queueJob.delay(thinkerProperties.getConfig().getRabbitRetryDelay());
                }
            }
        } else {
            log.error("no QueueJob for msg: " + msg);
        }
    }

    //基础队列监听
    @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue(name = "#{_defaultQueueBean.name}"))
    public void defaultReceive(String msg) {
        _receive(msg);
    }

    //订单队列监听
    @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue(name = "#{_orderQueueBean.name}"))
    public void orderReceive(String msg) {
        _receive(msg);
    }
}
