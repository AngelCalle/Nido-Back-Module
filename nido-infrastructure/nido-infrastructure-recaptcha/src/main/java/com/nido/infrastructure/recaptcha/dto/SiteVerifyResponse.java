package com.nido.infrastructure.recaptcha.dto;

import java.util.List;

/**
 * Esta clase implementa el DTO para serializar la verificación del sitio de
 * Google respuesta de servicio, basada en <a href= "#
 * {@link https://developers.google.com/recaptcha/docs/v3#site_verify_response}
 * "> reCaptcha APIv3 specifications </a>.
 */

public class SiteVerifyResponse {

	private boolean success; /* true|false */
	private String challenge_ts; /* Sello de tiempo con formato ISO */
	private String hostname; /* url / IP que envió la solicitud reCaptcha */
	private double score;
	private String action; /* el nombre de la acción para esta solicitud (importante de verificar) */
	private List<String> errorcodes;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getChallenge_ts() {
		return challenge_ts;
	}

	public void setChallenge_ts(String challenge_ts) {
		this.challenge_ts = challenge_ts;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getErrorcodes() {
		return errorcodes;
	}

	public void setErrorcodes(List<String> errorcodes) {
		this.errorcodes = errorcodes;
	}

}
