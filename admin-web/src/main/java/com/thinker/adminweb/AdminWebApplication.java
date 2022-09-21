package com.thinker.adminweb;

import com.thinker.framework.framework.support.SpringContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@MapperScan(basePackages = {"com.thinker.framework", "com.thinker.queue", "com.thinker.adminweb", "com.thinker.minio"})
@ComponentScan(basePackages = {"com.thinker.framework", "com.thinker.queue", "com.thinker.adminweb", "com.thinker.minio"})
public class AdminWebApplication {

    public static void main(String[] args) {
        SpringContext.setApplicationContext(SpringApplication.run(AdminWebApplication.class, args));
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
        return factory;
    }
}
