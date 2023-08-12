package com.yonlog;

import com.yonlog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class YonlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(YonlogApplication.class, args);
    }

}
