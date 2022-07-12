package com.thinker.framework.framework;

import com.thinker.framework.framework.properties.ThinkerProperties;
import com.thinker.framework.framework.service.RedisService;
import com.thinker.framework.framework.service.ThreadService;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.widgets.*;
import com.thinker.framework.renders.ThinkerForm;
import com.thinker.framework.renders.ThinkerPage;
import com.thinker.framework.renders.ThinkerTable;
import com.thinker.framework.renders.abstracts.RunClosure;

import java.util.HashMap;
import java.util.Map;

public class ThinkerAdmin {
    public static final String version = "0.0.1";

    /**
     * 单例存储
     */
    private static final Map<String, Object> widgets = new HashMap<>();

    /**
     * 获取到request方法
     * @return
     */
    public static ThinkerRequest request() {
        if(!widgets.containsKey("request")) {
            widgets.put("request", new ThinkerRequest());
        }
        return (ThinkerRequest) widgets.get("request");
    }

    /**
     * 便捷返回
     * @return
     */
    public static ThinkerResponse response() {
        return new ThinkerResponse();
    }

    /**
     * 文件方法
     * @return
     */
    public static ThinkerFile file() {
        if(!widgets.containsKey("file")) {
            widgets.put("file", new ThinkerFile());
        }
        return (ThinkerFile) widgets.get("file");
    }

    /**
     * 加密方法
     * @return
     */
    public static ThinkerEncrypt encrypt() {
        if(!widgets.containsKey("encrypt")) {
            widgets.put("encrypt", new ThinkerEncrypt());
        }
        return (ThinkerEncrypt) widgets.get("encrypt");
    }

    /**
     * 获取腾讯相关
     * @return
     */
    public static ThinkerWechat wechat() {
        if(!widgets.containsKey("wechat")) {
            widgets.put("wechat", new ThinkerWechat());
        }
        return (ThinkerWechat) widgets.get("wechat");
    }

    /**
     * 缓存Redis
     * @return
     */
    public static RedisService redis() {
        return SpringContext.getBean(RedisService.class);
    }

    /**
     * 运行时内容保存
     * @return
     */
    public static ThreadService thread() {
        return SpringContext.getBean(ThreadService.class);
    }

    /**
     * 配置文件
     * @return
     */
    public static ThinkerProperties properties() {
        return SpringContext.getBean(ThinkerProperties.class);
    }

    /**
     * 界面模板
     * @return
     */
    public static ThinkerPage page() { return new ThinkerPage(); }
    public static ThinkerPage page(ThinkerPage.RunClosure closure) { return new ThinkerPage(closure); }

    /**
     * 列表样式
     * @return
     */
    public static ThinkerTable table() { return new ThinkerTable(); }
    public static ThinkerTable table(RunClosure<ThinkerTable> closure) { return new ThinkerTable().runClosure(closure); }

    /**
     * 列表样式
     * @return
     */
    public static ThinkerForm form() { return new ThinkerForm(); }
    public static ThinkerForm form(RunClosure<ThinkerForm> closure) { return new ThinkerForm().runClosure(closure); }

}
