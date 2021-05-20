package com.nido.infrastructure.recaptcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class NidoRecaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoRecaptchaApplication.class, args);
		log.info("Nido Oauth Server iniciado correctamente... !!");
	}

}
