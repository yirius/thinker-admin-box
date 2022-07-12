package com.thinker.framework.framework.widgets;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.google.common.collect.Maps;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import lombok.extern.slf4j.Slf4j;

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

    private static Map<String, WxMaService> minappInstance = Maps.newHashMap();

    public ThinkerWechat() {
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
            return minappInstance.get(minappInstance.keySet().toArray()[0]);
        } else {
            throw new ThinkerException("未找到对应minappid");
        }
    }
}
