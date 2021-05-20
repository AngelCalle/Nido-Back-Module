package com.nido.infrastructure.gateway.filter;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 
 * @author Sr.Cekas
 * Ejecutar la lógica antes y despues de que se envíe la solicitud de proxy.
 *
 */

@Component
public class FirstPreLastPostGlobalFilter implements GlobalFilter, Ordered {
	
	private static final Logger log = LoggerFactory.getLogger(FirstPreLastPostGlobalFilter.class);

    @Override
    public Mono<Void> filter(
    		ServerWebExchange exchange,
    		GatewayFilterChain chain
    ) {
    	log.info("First Pre Global Filter");;
        return chain.filter(exchange)
          .then(Mono.fromRunnable(() -> {
        	  log.info("Last Post Global Filter 3\n\n");
//        	  Imprimo todas las propiedades cada vez que se pasa el filtro
        	  final Set<String> keys = exchange.getAttributes().keySet();
        	  for (final String key : keys) {
        		   log.info("{}", key + "\t" + exchange.getAttributes().get(key));
        	  }
            }));
    }

    
    // Debido a la naturaleza de la cadena de filtros,
    // un filtro con menor precedencia (un orden menor en la cadena)
    // ejecutará su lógica "pre" en una etapa anterior, pero su implementación "post"
    // se invocará más tarde:
    @Override
    public int getOrder() {
        return -1;
    }

}