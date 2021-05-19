package com.nido.infrastructure.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class NidoGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoGatewayApplication.class, args);
    	log.info("Nido Gateway Server iniciado .... !!");    	
	}
	
}
