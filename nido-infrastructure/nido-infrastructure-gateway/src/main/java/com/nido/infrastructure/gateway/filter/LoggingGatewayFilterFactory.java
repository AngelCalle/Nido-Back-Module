package com.nido.infrastructure.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class LoggingGatewayFilterFactory extends 
  AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {
	
	private static final Logger log = LoggerFactory.getLogger(LoggingGatewayFilterFactory.class);

    public LoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Pre-processing
            if (config.isPreLogger()) {
            	log.info("Pre GatewayFilter logging: " + config.getBaseMessage());
            }
            return chain.filter(exchange)
              .then(Mono.fromRunnable(() -> {
                  // Post-processing
                  if (config.isPostLogger()) {
                	  log.info("Post GatewayFilter logging: " + config.getBaseMessage());
                  }
              }));
        };
    }

    public static class Config {

    	// Mensaje personalizado que se incluirá en la entrada del registro.
        private String baseMessage;
        // Bandera que indica si el filtro debe registrarse antes de reenviar la solicitud del servicio proxy.
        private boolean preLogger;
        // Bandera que indica si el filtro debe registrarse después de recibir la respuesta del servicio proxy.
        private boolean postLogger;
        
        public Config() {
    	}
    	
    	public Config(String baseMessage, boolean preLogger, boolean postLogger) {
    		super();
    		this.baseMessage = baseMessage;
    		this.preLogger = preLogger;
    		this.postLogger = postLogger;
    	}
    	
    	public String getBaseMessage() {
    		return baseMessage;
    	}
    	
    	public void setBaseMessage(String baseMessage) {
    		this.baseMessage = baseMessage;
    	}
    	
    	public boolean isPreLogger() {
    		return preLogger;
    	}
    	
    	public void setPreLogger(boolean preLogger) {
    		this.preLogger = preLogger;
    	}
    	
    	public boolean isPostLogger() {
    		return postLogger;
    	}
    	
    	public void setPostLogger(boolean postLogger) {
    		this.postLogger = postLogger;
    	}
    }
}