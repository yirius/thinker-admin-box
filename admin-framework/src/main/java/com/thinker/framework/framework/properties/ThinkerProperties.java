package com.thinker.framework.framework.properties;

import com.thinker.framework.framework.properties.factory.YamlConfigFactory;
import com.thinker.framework.framework.properties.thinker.ConfigProperties;
import com.thinker.framework.framework.properties.thinker.TokenProperties;
import com.thinker.framework.framework.properties.thinker.WechatProperties;
import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@SpringBootConfiguration
@PropertySource(value = {"classpath:thinker${thinker.profiles.active:}.yml"},factory = YamlConfigFactory.class)
@ConfigurationProperties(prefix = "thinker")
public class ThinkerProperties {
    private Map<String, String> sharding = new HashMap<>();
    private TokenProperties token = new TokenProperties();
    private ConfigProperties config = new ConfigProperties();
    private WechatProperties wechat = new WechatProperties();
}
