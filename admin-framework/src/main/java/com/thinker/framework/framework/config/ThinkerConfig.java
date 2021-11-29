package com.thinker.framework.framework.config;

import com.thinker.framework.framework.properties.ThinkerProperties;
import com.thinker.framework.framework.support.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @title ThinkerConfig
 * @description 便捷调用方法
 * @time 2020/2/14 11:26 下午
 * @return
 **/
@Slf4j
@Configuration
public class ThinkerConfig {

    ThinkerProperties thinkerProperties;
    public ThinkerConfig(ThinkerProperties thinkerProperties) {
        this.thinkerProperties = thinkerProperties;
    }

    /**
     * XssFilter Bean
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter(thinkerProperties));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/admin.html");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
