package com.nido.infrastructure.recaptcha.config;

import org.springframework.context.annotation.Import;

/**
 * Definición de la interfaz que permite a una aplicación de arranque habilitar
 * y utilizar las capacidades del servicio NidoRecaptchaApplication dentro de su
 * contexto. Es importante para facilitar la configuración automática y el
 * despliegue del microservicio NidoRecaptchaApplication en un contexto de
 * aplicación de arranque de primavera de terceros.
 */
@Import(AppConfiguration.class)
public @interface EnableGoogleRecaptchaService {
}