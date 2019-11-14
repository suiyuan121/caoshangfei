package com.zj.caoshangfei.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "caoshangfei")
@Data
public class CaoshangfeiProperties {


    private String host;


}
