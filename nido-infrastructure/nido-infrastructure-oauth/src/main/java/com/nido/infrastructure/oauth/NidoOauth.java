package com.nido.infrastructure.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class NidoOauth  {
	
    public static void main( String[] args ) {
    	SpringApplication.run(NidoOauth.class, args);
    	log.info("Nido Oauth Server iniciado .... !!");    	
    }

}
