package com.coca.assemble.authsecurity.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {
    /**
     * 密钥KEY
     */
    @Setter
    private String secret;
    /**
     * TokenKey
     */
    @Setter
    private String tokenHeader;
    /**
     * Token前缀字符
     */
    @Setter
    private String tokenPrefix;
    /**
     * 过期时间
     */
    private Integer expiration;
    /**
     * 不需要认证的接口
     */
    @Setter
    private String antMatchers;



    public void setExpiration(Integer expiration) {
        this.expiration = expiration * 1000;
    }

}
