package com.coca.assemble.apigateway.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@EnableConfigurationProperties(AssembleSecurityProperties.class)
@ConfigurationProperties(prefix = "assemble.security")
public class AssembleSecurityProperties {

    /**
     * 访问auth-security服务的loadBalance地址，服务名
     */
    private String authUrl;

    /**
     * 不需要进行鉴权的接口和path。
     */
    private String[] ignorePath;

}

