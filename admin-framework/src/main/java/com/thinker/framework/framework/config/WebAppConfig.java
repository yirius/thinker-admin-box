package com.thinker.framework.framework.config;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.properties.ThinkerProperties;
import com.thinker.framework.framework.support.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @title WebAppConfig
 * @description 便捷调用方法
 * @time 2020/2/14 3:57 下午
 * @return
 **/
@Slf4j
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    Environment environment;

    // 记录参数
    private final ThinkerProperties thinkerProperties;
    public WebAppConfig(ThinkerProperties thinkerProperties) {
        this.thinkerProperties = thinkerProperties;
    }

    //添加对应文件夹的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+thinkerProperties.getConfig().getUploadPath()+"**")
                .addResourceLocations("file:" + ThinkerAdmin.file().getDirPath(thinkerProperties.getConfig().getUploadPath()));

        // 注册剩余的指向
        thinkerProperties.getConfig().getResourcePaths().forEach((s, s1) -> {
            String filePath = null;
            if(s1.startsWith("$")) {
                filePath = environment.getProperty(s1.replace("$", ""));
            }
            if(filePath == null || Validator.isEmpty(filePath)) {
                filePath = ThinkerAdmin.file().getDirPath(s1);
            }

            registry.addResourceHandler("/"+s+"/**")
                    .addResourceLocations("file:" + filePath);
        });

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // **代表所有路径
                // allowOrigin指可以通过的ip，*代表所有，可以使用指定的ip，多个的话可以用逗号分隔，默认为*
                .allowedOrigins(thinkerProperties.getConfig().getAllowedOrigins())
                .allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE") // 指请求方式 默认为*
                .allowCredentials(false) // 支持证书，默认为true
                .allowedHeaders("*")
                .maxAge(3600); // 最大过期时间，默认为-1;
    }
}
