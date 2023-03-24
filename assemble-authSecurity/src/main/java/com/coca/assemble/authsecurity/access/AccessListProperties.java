package com.coca.assemble.authsecurity.access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ToString
@Setter
@Getter
@ConfigurationProperties(prefix = "access-list")
@RefreshScope
@Configuration
@EnableConfigurationProperties(AccessListProperties.class)
public class AccessListProperties {
    /**
     * 白名单
     */
    private volatile List<String> whiteList;
    /**
     * 黑名单
     */
    private volatile List<String> blackList;
}
