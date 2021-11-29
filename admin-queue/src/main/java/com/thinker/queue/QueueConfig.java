package com.thinker.queue;

import com.thinker.framework.framework.properties.ThinkerProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @title QueueConfig
 * @description 便捷调用方法
 * @time 2020/2/26 6:05 下午
 * @return
// **/
@Configuration
public class QueueConfig {

    @Autowired
    private Environment env;

    @Autowired
    private ThinkerProperties thinkerProperties;

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(
                thinkerProperties.getConfig().getRabbitExchange() + "_" + env.getActiveProfiles()[0],
                "x-delayed-message",true, false, args
        );
    }

    @Bean
    public Queue _defaultQueueBean() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "classic");
        return new Queue(
                thinkerProperties.getConfig().getRabbitQueueDefault() + "_" + env.getActiveProfiles()[0],
                true, false, false, args
        );
    }

    @Bean
    public Binding defaultQueueBinding() {
        return BindingBuilder.bind(_defaultQueueBean()).to(delayExchange())
                .with(thinkerProperties.getConfig().getRabbitQueueDefault() + "_" + env.getActiveProfiles()[0]).noargs();
    }

    @Bean
    public Queue _orderQueueBean() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "classic");
        return new Queue(
                thinkerProperties.getConfig().getRabbitQueueOrder() + "_" + env.getActiveProfiles()[0],
                true, false, false, args
        );
    }

    @Bean
    public Binding orderQueueBinding() {
        return BindingBuilder.bind(_orderQueueBean()).to(delayExchange())
                .with(thinkerProperties.getConfig().getRabbitQueueOrder() + "_" + env.getActiveProfiles()[0]).noargs();
    }
}
