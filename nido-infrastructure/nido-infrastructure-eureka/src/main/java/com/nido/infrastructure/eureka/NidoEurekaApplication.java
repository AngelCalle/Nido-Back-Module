package com.nido.infrastructure.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * El objetivo de Eureka es servir de Servidor de nombres que
 * permite registrar microservicios.
 * @EnableEurekaServer Para habilitar Eureka.
 */
@EnableEurekaServer
@SpringBootApplication
public class NidoEurekaApplication {
	
    public static void main( String[] args ) {
    	SpringApplication.run(NidoEurekaApplication.class, args);
    	log.info("Nido Eureka Server iniciado .... !!");   
    }

}
