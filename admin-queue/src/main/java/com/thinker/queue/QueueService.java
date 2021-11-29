package com.thinker.queue;

import cn.hutool.core.util.ClassLoaderUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.properties.ThinkerProperties;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @title QueueService
 * @description 便捷调用方法
 * @time 2020/2/26 6:08 下午
 * @return
 **/
@Slf4j
@Service
public class QueueService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @Autowired
    private ThinkerProperties thinkerProperties;

    /**
     * @title send
     * @description 基础发送方法，自定义交换机和队列名
     * @author YangYuanCe
     * @param exchangeName
     * @param queueName
     * @param seconds
     * @return
     **/
    public void send(String exchangeName, String queueName, QueueJob queueJob, Integer seconds) {
        //重置参数
        queueJob.setExchange(exchangeName);
        queueJob.setQueue(queueName);
        rabbitTemplate.convertAndSend(exchangeName, queueName, JSON.toJSONString(queueJob), message -> {
            if(seconds > 0) {
                message.getMessageProperties().setDelay(seconds * 1000);
            } else {
                message.getMessageProperties();
            }
            return message;
        });
    }

    /**
     * @title sendDefault
     * @description 发送的基础队列
     * @author YangYuanCe
     * @param runClass
     * @param data
     * @param seconds
     * @return
     **/
    public void sendDefault(String runClass, Object data, Integer seconds) {
        if(ClassLoaderUtil.isPresent(runClass)) {
            QueueJob queueJob = new QueueJob();
            queueJob.setRunClass(runClass);
            queueJob.setData(data);
            send(
                    thinkerProperties.getConfig().getRabbitExchange() + "_" + env.getActiveProfiles()[0],
                    thinkerProperties.getConfig().getRabbitQueueDefault() + "_" + env.getActiveProfiles()[0],
                    queueJob,
                    seconds
            );
        } else {
            throw new ThinkerException("当前指定的队列执行类未找到");
        }
    }

    public void sendDefault(String runClass, Object data) {
        sendDefault(runClass, data, 0);
    }

    /**
     * @title sendOrder
     * @description 发送的订单队列
     * @author YangYuanCe
     * @param runClass
     * @param data
     * @param seconds
     * @return
     **/
    public void sendOrder(String runClass, Object data, Integer seconds) {
        if(ClassLoaderUtil.isPresent(runClass)) {
            QueueJob queueJob = new QueueJob();
            queueJob.setRunClass(runClass);
            queueJob.setData(data);
            send(
                    thinkerProperties.getConfig().getRabbitExchange() + "_" + env.getActiveProfiles()[0],
                    thinkerProperties.getConfig().getRabbitQueueOrder() + "_" + env.getActiveProfiles()[0],
                    queueJob,
                    seconds
            );
        } else {
            throw new ThinkerException("当前指定的队列执行类未找到");
        }
    }

    public void sendOrder(String runClass, Object data) {
        sendOrder(runClass, data, 0);
    }
}
