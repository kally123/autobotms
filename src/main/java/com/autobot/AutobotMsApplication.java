package com.autobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(name = "restProps", value = {"classpath:restProject.properties"})
public class AutobotMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutobotMsApplication.class, args);
    }
}
