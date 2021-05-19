package com.nido.infrastructure.gateway.filter;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sr.Cekas
 * Ejecutar la lógica antes de que se envíe la solicitud de proxy.
 *
 */
@Slf4j
@Component
public class AccountPreFilter implements GlobalFilter {
	
	@Override
    public Mono<Void> filter(
      ServerWebExchange exchange,
      GatewayFilterChain chain) {
    	log.info("Global Pre Filter executed");
        return chain.filter(exchange);
    }
}