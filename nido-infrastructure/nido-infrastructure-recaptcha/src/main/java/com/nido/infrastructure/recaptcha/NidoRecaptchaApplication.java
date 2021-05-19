package com.nido.infrastructure.recaptcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class NidoRecaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NidoRecaptchaApplication.class, args);
	}

}
