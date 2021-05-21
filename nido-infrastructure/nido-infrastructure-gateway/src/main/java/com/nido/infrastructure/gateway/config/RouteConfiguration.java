package com.nido.infrastructure.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes().build();
	}

//    segunda opcion de redireccion en caso de no realizarla mediante el archivo properties
	@Bean
	public RouteLocator restaurantRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				
				/* INFRASTRUCTURE */
				.route("localhost:9999", a ->
					a.path("/api-v1/config-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://config-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/admin-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://admin-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/actuator-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://actuator-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/oauth-api/auth/token")
					.filters(f -> f.stripPrefix(1)).uri("lb://oauth-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/recaptcha-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://recaptcha-server"))
	
				/* BUSINESS */
				.route("localhost:9999", a ->
					a.path("/api-v1/clients-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://clients-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/users-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://users-server"))
				.route("localhost:9999", a ->
					a.path("/api-v1/mail-api/**")
					.filters(f -> f.stripPrefix(1)).uri("lb://mail-server"))

				.build();
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
