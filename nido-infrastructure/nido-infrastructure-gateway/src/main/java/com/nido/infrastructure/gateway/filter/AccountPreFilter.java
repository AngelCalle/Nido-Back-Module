package com.nido.infrastructure.gateway.filter;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 
 * @author Sr.Cekas
 * Ejecutar la lógica antes de que se envíe la solicitud de proxy.
 *
 */
@Component
public class AccountPreFilter implements GlobalFilter {
	
	private static final Logger log = LoggerFactory.getLogger(AccountPreFilter.class);

    @Override
    public Mono<Void> filter(
      ServerWebExchange exchange,
      GatewayFilterChain chain) {
    	log.info("Global Pre Filter executed");
        return chain.filter(exchange);
    }
}