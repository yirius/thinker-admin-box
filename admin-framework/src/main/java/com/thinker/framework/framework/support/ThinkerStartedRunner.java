package com.thinker.framework.framework.support;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.properties.ThinkerProperties;
import com.thinker.framework.framework.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
public class ThinkerStartedRunner implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThinkerProperties thinkerProperties;

    @Value("${server.port:4040}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 测试 Redis连接是否正常
            redisService.has("thinker_test");
        } catch (Exception e) {
            log.error("\n" +
                    "    _________    ______ \n" +
                    "   / ____/   |  /  _/ / \n" +
                    "  / /_  / /| |  / // /  \n" +
                    " / __/ / ___ |_/ // /___\n" +
                    "/_/   /_/  |_/___/_____/\n" +
                    "                        \n");
            log.error("Thinker 启动失败，{}", e.getMessage());
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            // 关闭 Thinker
            context.close();
        }
        if (context.isActive()) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            if (Validator.isNotEmpty(contextPath))
                url += contextPath;
            url += thinkerProperties.getConfig().getLoginUrl();
            log.info("\n" +
                    "   _____ __  ______________________________\n" +
                    "  / ___// / / / ____/ ____/ ____/ ___/ ___/\n" +
                    "  \\__ \\/ / / / /   / /   / __/  \\__ \\\\__ \\ \n" +
                    " ___/ / /_/ / /___/ /___/ /___ ___/ /__/ / \n" +
                    "/____/\\____/\\____/\\____/_____//____/____/  \n");
            log.info("Thinker 系统启动完毕, 当前环境: {}, 地址：{}", active, url);
        }
    }
}
