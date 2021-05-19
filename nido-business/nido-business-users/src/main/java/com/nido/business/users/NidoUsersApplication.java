package com.nido.business.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//Habilitar el mecanismo de auditoría de Spring Data JPA, @CreatedDate, @LastModifiedDate
@EnableJpaAuditing
//@EnableFeignClients
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class NidoUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoUsersApplication.class, args);
	}
	
	// @Bean
	// public BCryptPasswordEncoder bCryptPasswordEncoder() {
	// return new BCryptPasswordEncoder();
	// }
	
}