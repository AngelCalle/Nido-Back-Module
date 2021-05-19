package com.nido.infrastructure.recaptcha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Esta clase implementa un componente spring-boot que accede a * .properties
 * archivo de configuración para el microservicio NidoRecaptchaApplication,
 * manipulando el prefijo propiedades relacionadas como tipos de datos Java
 * primitivos.
 */
@Component
@ConfigurationProperties(prefix = "google.recaptcha")
public class GoogleReCaptchaPropeties {

	private String verificationEndpointUrl; /* punto final de google para la verificación del token del sitio */
	private String siteKey; /* esto es público y es utilizado por el sitio web para realizar la peticion. */
	private String secretKey; /* llave secreta */

	public String getVerificationEndpointUrl() {
		return verificationEndpointUrl;
	}

	public void setVerificationEndpointUrl(String verificationEndpointUrl) {
		this.verificationEndpointUrl = verificationEndpointUrl;
	}

	public String getSiteKey() {
		return siteKey;
	}

	public void setSiteKey(String siteKey) {
		this.siteKey = siteKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
