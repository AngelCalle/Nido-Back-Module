package com.nido.business.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class NidoMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoMailApplication.class, args);
    	log.info("Nido Mail Server iniciado.... !!");   
	}
	
}