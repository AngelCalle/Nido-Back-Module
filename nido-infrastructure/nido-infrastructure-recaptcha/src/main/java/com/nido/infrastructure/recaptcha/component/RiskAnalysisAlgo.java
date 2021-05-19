package com.nido.infrastructure.recaptcha.component;

import org.springframework.stereotype.Component;

/**
 * Análisis de riesgos Algo Este componente brinda soporte para el análisis de
 * riesgo de transacción, según las especificaciones de <a href= "#
 * {@link https://developers.google.com/recaptcha/docs/v3#interpreting_the_score}
 * "> Google reCaptcha APIv3 verification score interpretation</a>.
 */
@Component
public class RiskAnalysisAlgo {

	private final double LOWER_THRESHOLD = 0.5;

	/**
	 * Este método implementa el cálculo del riesgo de transacción.
	 * 
	 * @param reCaptchaScore la puntuación de la respuesta de verificación del sitio
	 *                       de recaptcha
	 * @return true para transacciones riesgosas, de lo contrario falso.
	 */
	public boolean isSecureTransaction(double reCaptchaScore) {
		return (reCaptchaScore > LOWER_THRESHOLD) ? true : false;
	}

}