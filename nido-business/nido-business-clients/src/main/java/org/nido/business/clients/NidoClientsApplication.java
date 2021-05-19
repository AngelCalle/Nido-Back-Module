package org.nido.business.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class NidoClientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoClientsApplication.class, args);
    	log.info("Nido Clients Server iniciado .... !!");   
	}
	
}