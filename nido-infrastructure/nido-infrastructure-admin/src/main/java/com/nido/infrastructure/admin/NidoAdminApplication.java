package com.nido.infrastructure.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration
public class NidoAdminApplication {
	
    public static void main( String[] args ) {
    	SpringApplication.run(NidoAdminApplication.class, args);
    }

}
