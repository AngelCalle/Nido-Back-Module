package com.nido.infrastructure.recaptcha.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Esta clase implementa la configuración del microservicio
 * NidoRecaptchaApplication, para definir los beans relacionados con el servicio
 * utilizando la configuración de Java y permitir su implementación en el
 * contexto de una aplicación de arranque de primavera.
 */
@Configuration
@ComponentScan(basePackages = "com.nido.infrastructure.recaptcha")
public class AppConfiguration {
}