package com.sergeb.luxgatewayjava;

import com.sergeb.luxgatewayjava.configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.encrypt.RsaProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
