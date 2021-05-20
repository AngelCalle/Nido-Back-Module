package com.nido.infrastructure.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

	private static final Logger log = LoggerFactory.getLogger(RouteConfiguration.class);

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes().build();
	}

//    segunda opcion de redireccion en caso de no realizarla mediante el archivo properties
	@Bean
	public RouteLocator restaurantRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("localhost:9999", a ->
					a.path("/api-v1/config-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://config-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/auth-api/oauth/token")
					.filters(f -> f.stripPrefix(1)).uri("lb://auth-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/resource-api/privado")
					.filters(f -> f.stripPrefix(1)).uri("lb://resource-server")).build();
//        
//        return builder.routes()
//                .route(p -> p
//                        .path("/**")
//                        .filters(f -> f.hystrix(config -> {
////                            config
////                                    .setName("retail-account")
////                                    .setFallbackUri("forward:/fallback/accountFallback");
//                        }))
////	                        .filter(new AccountPreFilter().apply(new AccountPreFilter.Config()), 0)
////	                        .filter(new AccountPostFilter().apply(new AccountPostFilter.Config()), 1))
//                        	.uri("lb://caramelo-server")
//                        )
//                		.build();

	}

}
