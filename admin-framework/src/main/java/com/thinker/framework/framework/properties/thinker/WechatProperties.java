package com.thinker.framework.framework.properties.thinker;

import lombok.Data;

import java.util.List;

/**
 * @title WechatMinappProperties
 * @description 便捷调用方法
 * @time 2020/5/12 4:52 下午
 * @return
 **/
@Data
public class WechatProperties {

    private List<MinappConfig> minapp;

    @Data
    public static class MinappConfig {
        /**
         * 设置微信小程序的appid
         */
        private String appid;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;

        /**
         * 设置微信小程序消息服务器配置的token
         */
        private String token;

        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey
         */
        private String aesKey;

        /**
         * 消息格式，XML或者JSON
         */
        private String msgDataFormat;
    }
}
