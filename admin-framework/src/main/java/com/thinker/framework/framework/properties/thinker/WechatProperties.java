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
    private List<MinappConfig> mpapp;
    private List<MpPayConfig> payapp;

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


    @Data
    public static class MpPayConfig {
        /**
         * 设置微信小程序的appid
         */
        private String appid;
        /**
         * 设置微信支付商户号
         */
        private String mchId;
        /**
         * 设置微信支付V2秘钥
         */
        private String mchKey;
        /**
         * 设置微信支付V3秘钥
         */
        private String mchKeyPlus;
        /**
         * 设置微信支付V3秘钥
         */
        private String keyPath;
        /**
         * 设置微信支付V3秘钥
         */
        private String privateCertPath;
        /**
         * 设置微信支付V3秘钥
         */
        private String privateKeyPath;
    }
}
