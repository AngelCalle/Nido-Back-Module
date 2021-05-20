package com.nido.business.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//Habilitar el mecanismo de auditoría de Spring Data JPA, @CreatedDate, @LastModifiedDate
@EnableJpaAuditing
//@EnableFeignClients
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class NidoUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoUsersApplication.class, args);
    	log.info("Nido Clients Server iniciado.... !!");
	}
	
	// @Bean
	// public BCryptPasswordEncoder bCryptPasswordEncoder() {
	// return new BCryptPasswordEncoder();
	// }
	
}