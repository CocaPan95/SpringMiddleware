package com.coca.assemble.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfiguration {
    /**
     * 配置 Swagger的Docket的Bean实例
     * @return
     */
    @Bean
    public Docket docketClient(Environment environment){
        //设置要显示Swagger的环境
        Profiles profiles = Profiles.of("dev");
        //获取项目环境
        //通过 environment.acceptsProfiles 判断是否处在自己设定的环境的当中
        boolean flag = environment.acceptsProfiles(profiles);
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //设置是否启动Swagger
                .select()
                //RequestHandlerSelectors，配置要扫描的接口方法
                //basePackage：指定要扫描的包
                //any()：扫描全部
                //none()：都不扫描
                //withClassAnnotation()：扫描类上的注解——参数是一个注解的反射对象
                //withMethodAnnotation()：扫描方法上的注解——get post
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                //过滤地址
                .paths(PathSelectors.ant("/api/**"))
                //工厂模式DEFUALT_GROUP_NAME
                .build().groupName(Docket.DEFAULT_GROUP_NAME);
        if (flag){
            return docket.enable(true);
        }else {
            return docket.enable(false);
        }
    }



    /**
     * 配置Swagger 信息apiInfo
     * @return
     */
    private ApiInfo apiInfo(){
        //作者信息
        Contact DEFAULT_CONTACT = new Contact("assemble", "https://serial-of-mem.github.io/person-homepage/",
                "liboware@gmail.com");
        return new ApiInfo("assemble的SwaggerAPI文档",
                "组件平台服务！",
                "1.0",
                "https://serial-of-mem.github.io/person-homepage/",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
