package com.nido.infrastructure.oauth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

/**
 * Servidor de autorización.
 * El servidor de autorización valida los client y las user credenciales
 * y proporciona JWT.
 * 
 * Para firmar los JWTtokens generados usaremos un certificado autofirmado.
 * @author Sr.Cekas
 *
 */
@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class NidoOauth  {
	
    public static void main( String[] args ) {
    	SpringApplication.run(NidoOauth.class, args);
    	log.info("Nido Oauth Server iniciado .... !!");
    	
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
//		String encodedPassword = encoder.encode("caramelo");
//		log.info("\n\n" + encodedPassword + "\n\n");
    }

}
