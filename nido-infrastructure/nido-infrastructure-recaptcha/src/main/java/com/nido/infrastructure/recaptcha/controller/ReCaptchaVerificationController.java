package com.nido.infrastructure.recaptcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nido.infrastructure.recaptcha.component.RiskAnalysisAlgo;
import com.nido.infrastructure.recaptcha.config.GoogleReCaptchaPropeties;
import com.nido.infrastructure.recaptcha.dto.ReCaptchaServiceResponse;
import com.nido.infrastructure.recaptcha.dto.SiteVerifyResponse;
import kong.unirest.Unirest;

/**
 * This controller implements the verification process for the google reCaptcha
 * service based on the APIv3 specifications described at
 * <a href= "#{@link https://developers.google.com/recaptcha/docs/verify}
 * ">verification section</a>.
 */
@RestController("ReCaptchaVerificationController")
@RequestMapping("/recaptcha-service")
public class ReCaptchaVerificationController {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private GoogleReCaptchaPropeties captchaProperties;
    @Autowired
    private RiskAnalysisAlgo riskAnalysisAlgo;

    /**
     * This method implements the endpoint based on the specifications of
     * <a href="#{@link https://developers.google.com/recaptcha/docs/v3}">Google
     * reCaptcha APIv3</a>.
     * 
     * @param gCaptchaToken: token sent from front-end side *
     * @return JSON object in form <validation_result, protected_domain>, packed
     *         into <a href=
     *         "#{@link https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html}">http
     *         response</a>.
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyToken(@NonNull @RequestParam("recaptchatoken") String gCaptchaToken) {
	try {
	    /**
	     * Post the ReCaptcha APIv3 Request (see here:
	     * https://developers.google.com/recaptcha/docs/verify#api_request)
	     */
	    String siteVerificationResponse = Unirest.post(captchaProperties.getVerificationEndpointUrl())
		    .queryString("secret", captchaProperties.getSecretKey()).queryString("response", gCaptchaToken)
		    .asString().getBody();

	    /**
	     * Serialize the site verification JSON response from APIv3 google endpoint, to
	     * object (see here for details:
	     * https://developers.google.com/recaptcha/docs/v3#site_verify_response)
	     */
	    SiteVerifyResponse gRecaptchaSVR = mapper.readValue(siteVerificationResponse, SiteVerifyResponse.class);

	    /* Estimate the transaction risk and evaluate the request reliability */
	    boolean isSecureRequest = riskAnalysisAlgo.isSecureTransaction(gRecaptchaSVR.getScore());

	    /**
	     * Return the evaluation result as the service response in form
	     * <is_valid_request, protected_domain_url>
	     */
	    return ResponseEntity.status(HttpStatus.OK).body(new ReCaptchaServiceResponse(
		    (gRecaptchaSVR.isSuccess() && isSecureRequest), gRecaptchaSVR.getAction()));
	} catch (Exception e) {
	    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ReCaptchaServiceResponse(false, null));
	}
    }
}