package com.thinker.framework.framework.widgets;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @title ThinkerWechat
 * @description 便捷调用方法
 * @time 2020/5/12 5:10 下午
 * @return
 **/
@Slf4j
public class ThinkerWechat {

    private static Map<String, WxMpService> mpInstance = Maps.newHashMap();
    private static Map<String, WxPayService> payInstance = Maps.newHashMap();
    private static Map<String, WxMaService> minappInstance = Maps.newHashMap();

    public ThinkerWechat() {
        if(ThinkerAdmin.properties().getWechat().getMinapp().size() > 0) {
            minappInstance = ThinkerAdmin.properties().getWechat().getMinapp().stream().map(minappConfig -> {
                WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
                config.setAppid(minappConfig.getAppid());
                config.setSecret(minappConfig.getSecret());
                config.setToken(minappConfig.getToken());
                config.setAesKey(minappConfig.getAesKey());
                config.setMsgDataFormat(minappConfig.getMsgDataFormat());

                WxMaService service = new WxMaServiceImpl();

                service.setWxMaConfig(config);
                return service;
            }).collect(Collectors.toMap(
                    wxMaService -> wxMaService.getWxMaConfig().getAppid(),
                    wxMaService -> wxMaService
            ));
        }

        if(ThinkerAdmin.properties().getWechat().getMpapp().size() > 0) {
            mpInstance = ThinkerAdmin.properties().getWechat().getMpapp().stream().map(mpConfig -> {
                WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
                config.setAppId(mpConfig.getAppid());
                config.setSecret(mpConfig.getSecret());
                config.setToken(mpConfig.getToken());
                config.setAesKey(mpConfig.getAesKey());

                WxMpService service = new WxMpServiceImpl();
                service.setWxMpConfigStorage(config);

                return service;
            }).collect(Collectors.toMap(
                    wxMpService -> wxMpService.getWxMpConfigStorage().getAppId(),
                    wxMpService -> wxMpService
            ));
        }

        if(ThinkerAdmin.properties().getWechat().getPayapp().size() > 0) {
            payInstance = ThinkerAdmin.properties().getWechat().getPayapp().stream().map(payConfig -> {
                WxPayConfig config = new WxPayConfig();
                config.setAppId(payConfig.getAppid());
                config.setMchId(payConfig.getMchId());
                config.setMchKey(payConfig.getMchKey());
                config.setApiV3Key(payConfig.getMchKeyPlus());
                config.setKeyPath(payConfig.getKeyPath());
                config.setPrivateCertPath(payConfig.getPrivateCertPath());
                config.setPrivateKeyPath(payConfig.getPrivateKeyPath());

                WxPayService service = new WxPayServiceImpl();
                service.setConfig(config);

                return service;
            }).collect(Collectors.toMap(
                    wxPayService -> wxPayService.getConfig().getAppId(),
                    wxPayService -> wxPayService
            ));
        }
    }

    /**
     * 获取minapp实例
     * @param appid
     * @return
     */
    public WxMaService getMinapp(String appid) {
        if(minappInstance.containsKey(appid)) {
            return minappInstance.get(appid);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }

    public WxMaService getMinapp() {
        if(minappInstance.size() > 0) {
            return minappInstance.get(minappInstance.keySet().toArray(new String[]{})[0]);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }

    /**
     * 获取公众号实例
     * @param appid
     * @return
     */
    public WxMpService getOffical(String appid) {
        if(mpInstance.containsKey(appid)) {
            return mpInstance.get(appid);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }

    public WxMpService getOffical() {
        if(mpInstance.size() > 0) {
            return mpInstance.get(mpInstance.keySet().toArray(new String[]{})[0]);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }

    /**
     * 获取公众号实例
     * @param appid
     * @return
     */
    public WxPayService getPay(String appid) {
        if(payInstance.containsKey(appid)) {
            return payInstance.get(appid);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }

    public WxPayService getPay() {
        if(payInstance.size() > 0) {
            return payInstance.get(payInstance.keySet().toArray(new String[]{})[0]);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }
}
