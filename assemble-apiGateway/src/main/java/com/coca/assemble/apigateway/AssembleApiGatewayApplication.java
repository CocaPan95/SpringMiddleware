package com.coca.assemble.apigateway;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AssembleApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssembleApiGatewayApplication.class, args);
    }

}
