package com.coca.assemble.minio;

import cn.hutool.core.net.NetUtil;
import com.jvm123.minio.config.MinioProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class AssemblePlatformMinioClientApplication {

    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = SpringApplication.run(AssemblePlatformMinioClientApplication.class,args);
            log.info("请访问MINIO控制台：{}",applicationContext.getBean(MinioProperties.class).getEndpoint());
            ServerProperties serverProperties = applicationContext.getBean(ServerProperties.class);
            log.info("请访问swagger访问页面：{}", "http://"+ NetUtil.localIpv4s().stream().findFirst().get()+":"+
                    serverProperties.getPort()+"/doc.html");
        } catch (Exception e) {
            log.error("启动我失败！",e);
        }
    }

}
