package com.nido.infrastructure.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Sr.Cekas
 * Ejecutar la lógica despues de que se envíe la solicitud de proxy.
 *
 */
@Slf4j
@Component
public class AccountPostFilter {

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
