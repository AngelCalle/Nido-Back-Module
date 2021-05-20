package com.nido.common.api.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.script.ScriptException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nido.common.api.web.ApiError;


@ControllerAdvice
public class ExceptionsController {

    
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiError> missingServletRequestParameterException(
			final MissingServletRequestParameterException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> runtimeException(final RuntimeException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	}
	
	/**
	 * Spring tiene un mecanismo de traducción de excepciones incorporado,
	 * de modo que todas las excepciones lanzadas por los proveedores de persistencia de JPA se convierten en DataAccessException
	 * de Spring, para todos los beans anotados con @Repository (o configurados).
	 */
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ApiError> dataAccessException(final DataAccessException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	}

	/**
	 * Las excepciones en las que un reintento de la misma operación fallaría
	 * a menos que se corrija la causa de la excepción.
	 * Entonces, si pasa una identificación no existente, por ejemplo,
	 * fallará a menos que la identificación exista en la base de datos.
	 */
	@ExceptionHandler(NonTransientDataAccessException.class)
	public ResponseEntity<ApiError> nonTransientDataAccessException(final NonTransientDataAccessException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	}
	
	/**
	 * Son lo "opuesto" al anterior NonTransientDataAccessException (excepciones que son recuperables)
	 * después de algunos pasos de recuperación.
	 */
	@ExceptionHandler(RecoverableDataAccessException.class)
	public ResponseEntity<ApiError> recoverableDataAccessException(final RecoverableDataAccessException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	}
	
	/**
	 * Excepciones relacionadas con SQL, cuando se intenta procesar un script que no está bien formado, por ejemplo.
	 */
	@ExceptionHandler(ScriptException.class)
	public ResponseEntity<ApiError> scriptException(final ScriptException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	} 
	
	/**
	 * Excepción cuando la recuperación es posible sin ningún paso explícito,
	 * por ejemplo, cuando hay un tiempo de espera para la base de datos,
	 * lo reintenta después de unos segundos.
	 */
	@ExceptionHandler(TransientDataAccessException.class)
	public ResponseEntity<ApiError> transientDataAccessException(final TransientDataAccessException e) {
		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
	}

	private ResponseEntity<ApiError> getGeneralErrorResponseEntity(final HttpStatus status, final String message,
			final Object details) {

		List<?> listDetails = convertObjectToList(details);

		final ApiError response = new ApiError(LocalDateTime.now(), status, message, listDetails);

		return ResponseEntity.status(status).body(response);
	}

	public static List<?> convertObjectToList(Object obj) {
		List<?> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			list = Arrays.asList((Object[]) obj);
		} else if (obj instanceof Collection) {
			list = new ArrayList<>((Collection<?>) obj);
		}
		return list;
	}


}
