package com.nido.infrastructure.recaptcha.dto;

/**
 * Esta clase implementa el DTO para la comunicación entre el tercero cliente
 * frontend y el punto final de microservicio recaptcha.
 */
public class ReCaptchaServiceResponse {

	private boolean verificationIsValid;
	private String protectedDomain;

	/**
	 * Constructor para crear la respuesta del punto final de microservicio.
	 * 
	 * @param verificationIsValid: true/false dependiendo del resultado de la
	 *                             verificación recibido del servicio de
	 *                             verificación del sitio de Google.
	 * @param protectedDomain:     una URL que corresponde al punto final detrás del
	 *                             Verificación reCaptcha.
	 */
	public ReCaptchaServiceResponse(boolean verificationIsValid, String protectedDomain) {
		this.verificationIsValid = verificationIsValid;
		this.protectedDomain = protectedDomain;
	}

	public boolean isVerificationIsValid() {
		return verificationIsValid;
	}

	public void setVerificationIsValid(boolean verificationIsValid) {
		this.verificationIsValid = verificationIsValid;
	}

	public String getProtectedDomain() {
		return protectedDomain;
	}

	public void setProtectedDomain(String protectedDomain) {
		this.protectedDomain = protectedDomain;
	}

}