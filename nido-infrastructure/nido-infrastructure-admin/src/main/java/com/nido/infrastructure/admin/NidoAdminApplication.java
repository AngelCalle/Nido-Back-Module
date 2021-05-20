package com.nido.infrastructure.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@Slf4j
@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration
public class NidoAdminApplication {
	
    public static void main( String[] args ) {
    	SpringApplication.run(NidoAdminApplication.class, args);
		log.info("Nido Admin Server iniciado correctamente... !!");
    }

}
