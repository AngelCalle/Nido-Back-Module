package org.nido.business.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class NidoClientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoClientsApplication.class, args);
    	log.info("Nido Clients Server iniciado.... !!");
	}
	
}