package com.nido.infrastructure.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Sr.Cekas
 * Ejecutar la lógica despues de que se envíe la solicitud de proxy.
 *
 */
@Component
public class AccountPostFilter {

	private static final Logger log = LoggerFactory.getLogger(AccountPostFilter.class);

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
              .then(Mono.fromRunnable(() -> {
            	  log.info("Global Post Filter executed");
              }));
        };
    }
    
}
