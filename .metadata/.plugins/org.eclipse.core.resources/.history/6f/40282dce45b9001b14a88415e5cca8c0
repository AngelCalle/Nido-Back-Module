package com.nido.infrastructure.gateway.config;

import org.springframework.stereotype.Component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sr.Cekas
 * Implementación que se ha realizado para las pruebas.
 * Su funcionamiento consiste en ir retornando un estado diferente
 * cada vez que sea consultada (cada 30 segundos)
 * y una vez retornados todos retornar siempre el estado ‘UP’.
 * 
 */
@Slf4j
@Component
public class HealthCheck implements HealthCheckHandler {

	private int counter = -1;
	
	@Override
	public InstanceStatus getStatus(InstanceStatus currentStatus) {
		
		counter++;
		switch (counter) {
			case 0:
				log.info("\n\n" + InstanceStatus.OUT_OF_SERVICE + "\n\n");
				return InstanceStatus.OUT_OF_SERVICE;
			case 1:
				log.info("\n\n" + InstanceStatus.DOWN + "\n\n");
				return InstanceStatus.DOWN;
			case 2:
				log.info("\n\n" + InstanceStatus.STARTING + "\n\n");
				return InstanceStatus.STARTING;
			case 3:
				log.info("\n\n" + InstanceStatus.UNKNOWN + "\n\n");
				return InstanceStatus.UNKNOWN;
			default:
				log.info("\n\n" + InstanceStatus.UP + "\n\n");
				return InstanceStatus.UP;
		}
		
	}

}
