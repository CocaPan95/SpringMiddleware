package com.coca.assemble.authsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.coca.assemble.authsecurity.rbac.dao")
public class AssembleAuthSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssembleAuthSecurityApplication.class, args);
    }

}
