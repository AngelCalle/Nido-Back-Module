package com.nido.common.api.exception;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

//import org.apache.tomcat.util.ExceptionUtils;
/**
 * 
import org.baeldung.common.persistence.exception.MyEntityNotFoundException;
import org.baeldung.common.web.exception.ApiError;
import org.baeldung.common.web.exception.MyConflictException;
import org.baeldung.common.web.exception.MyResourceNotFoundException;
import org.baeldung.common.web.exception.ValidationErrorDTO;
 */

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.nido.common.api.dto.ValidationErrorDTO;
import com.nido.common.api.models.ApiError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API
    
    // 400

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(
    		final HttpMessageNotReadableException ex,
    		final HttpHeaders headers,
    		final HttpStatus status,
    		final WebRequest request
    		) {
        log.info("Bad Request: {}", ex.getMessage());
        log.debug("Bad Request: ", ex);

        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info("Bad Request: {}", ex.getMessage());
        log.debug("Bad Request: ", ex);

        final BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        final ValidationErrorDTO dto = processFieldErrors(fieldErrors);

        return handleExceptionInternal(ex, dto, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class })
    public final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        log.info("Bad Request: {}", ex.getLocalizedMessage());
        log.debug("Bad Request: ", ex);

        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 403
/*
 * 
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleEverything(final AccessDeniedException ex, final WebRequest request) {
        logger.error("403 Status Code", ex);

        final ApiError apiError = message(HttpStatus.FORBIDDEN, ex);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
    */ 
 
    // 404

    @ExceptionHandler({ EntityNotFoundException.class, EntityNotFoundException.class, ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        log.warn("Not Found: {}", ex.getMessage());

        final ApiError apiError = message(HttpStatus.NOT_FOUND, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, ConflictException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        log.warn("Conflict: {}", ex.getMessage());

        final ApiError apiError = message(HttpStatus.CONFLICT, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 415

    @ExceptionHandler({ InvalidMimeTypeException.class, InvalidMediaTypeException.class })
    protected ResponseEntity<Object> handleInvalidMimeTypeException(final IllegalArgumentException ex, final WebRequest request) {
        log.warn("Unsupported Media Type: {}", ex.getMessage());

        final ApiError apiError = message(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    // 500

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handle500s(final RuntimeException ex, final WebRequest request) {
        log.error("500 Status Code", ex);
        final ApiError apiError = message(HttpStatus.INTERNAL_SERVER_ERROR, ex);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // UTIL

    private ValidationErrorDTO processFieldErrors(final List<FieldError> fieldErrors) {
        final ValidationErrorDTO dto = new ValidationErrorDTO();
        log.error("UTIL");
        for (final FieldError fieldError : fieldErrors) {
            final String localizedErrorMessage = fieldError.getDefaultMessage();
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private ApiError message(final HttpStatus httpStatus, final Exception ex) {
    	log.info("\n\n ApiError messageApiError message");
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ex.getClass().getSimpleName();
        //devMessage = ExceptionUtils.getStackTrace(ex);
       
        return new ApiError(HttpStatus.BAD_REQUEST , message, devMessage);
        
   
    }
    
//	@ExceptionHandler(MissingServletRequestParameterException.class)
//	public ResponseEntity<ApiError> missingServletRequestParameterException(
//			final MissingServletRequestParameterException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	}
//
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<ApiError> runtimeException(final RuntimeException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	}
	
//	/**
//	 * Spring tiene un mecanismo de traducción de excepciones incorporado,
//	 * de modo que todas las excepciones lanzadas por los proveedores de persistencia de JPA se convierten en DataAccessException
//	 * de Spring, para todos los beans anotados con @Repository (o configurados).
//	 */
//	@ExceptionHandler(DataAccessException.class)
//	public ResponseEntity<ApiError> dataAccessException(final DataAccessException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	}
//
//	/**
//	 * Las excepciones en las que un reintento de la misma operación fallaría
//	 * a menos que se corrija la causa de la excepción.
//	 * Entonces, si pasa una identificación no existente, por ejemplo,
//	 * fallará a menos que la identificación exista en la base de datos.
//	 */
//	@ExceptionHandler(NonTransientDataAccessException.class)
//	public ResponseEntity<ApiError> nonTransientDataAccessException(final NonTransientDataAccessException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	}
//	
//	/**
//	 * Son lo "opuesto" al anterior NonTransientDataAccessException (excepciones que son recuperables)
//	 * después de algunos pasos de recuperación.
//	 */
//	@ExceptionHandler(RecoverableDataAccessException.class)
//	public ResponseEntity<ApiError> recoverableDataAccessException(final RecoverableDataAccessException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	}
//	
//	/**
//	 * Excepciones relacionadas con SQL, cuando se intenta procesar un script que no está bien formado, por ejemplo.
//	 */
//	@ExceptionHandler(ScriptException.class)
//	public ResponseEntity<ApiError> scriptException(final ScriptException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	} 
//	
//	/**
//	 * Excepción cuando la recuperación es posible sin ningún paso explícito,
//	 * por ejemplo, cuando hay un tiempo de espera para la base de datos,
//	 * lo reintenta después de unos segundos.
//	 */
//	@ExceptionHandler(TransientDataAccessException.class)
//	public ResponseEntity<ApiError> transientDataAccessException(final TransientDataAccessException e) {
//		return getGeneralErrorResponseEntity(HttpStatus.BAD_REQUEST, "bad_request", e.getMessage());
//	}
//
//	private ResponseEntity<ApiError> getGeneralErrorResponseEntity(final HttpStatus status, final String message,
//			final Object details) {
//
//		List<?> listDetails = convertObjectToList(details);
//
//		final ApiError response = new ApiError(LocalDateTime.now(), status, message, listDetails);
//
//		return ResponseEntity.status(status).body(response);
//	}
//
//	public static List<?> convertObjectToList(Object obj) {
//		List<?> list = new ArrayList<>();
//		if (obj.getClass().isArray()) {
//			list = Arrays.asList((Object[]) obj);
//		} else if (obj instanceof Collection) {
//			list = new ArrayList<>((Collection<?>) obj);
//		}
//		return list;
//	}


}
