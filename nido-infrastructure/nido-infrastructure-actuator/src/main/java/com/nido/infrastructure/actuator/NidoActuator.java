package com.nido.infrastructure.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class NidoActuator {
	
    public static void main( String[] args ) {
    	SpringApplication.run(NidoActuator.class, args);
		log.info("Nido Actuator Server iniciado correctamente... !!");
    }

}