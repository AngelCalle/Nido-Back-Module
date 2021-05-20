package com.nido.business.users.exceptions;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nido.business.users.web.ApiError;
import com.nido.business.users.web.ResponseEntityBuilder;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// 415 Se activa cuando el JSON no es válido
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {

		List<String> details = new ArrayList<String>();

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

		details.add(builder.toString());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Invalid JSON", details);

		return ResponseEntityBuilder.build(err);

	}

	// 400 Se activa cuando el JSON tiene un formato incorrecto.
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Malformed JSON request", details);

		return ResponseEntityBuilder.build(err);
	}

	// 400 Se activa cuando @Valid del método del controlador falla en la validación.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getObjectName() + " : " + error.getDefaultMessage()).collect(Collectors.toList());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Validation Errors", details);

		return ResponseEntityBuilder.build(err);
	}

	// handleMissingServletRequestParameter : triggers when there are missing
	// parameters
	// Esta excepción ocurre cuando un método de controlador no recibe un parámetro
	// requerido.
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getParameterName() + " parameter is missing");

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Missing Parameters", details);

		return ResponseEntityBuilder.build(err);
	}

	// handleMethodArgumentTypeMismatch : triggers when a parameter's type does not
	// match
	// ¡Esta excepción se lanza cuando un parámetro de método tiene el tipo
	// incorrecto!
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Mismatch Type", details);

		return ResponseEntityBuilder.build(err);
	}

	// handleConstraintViolationException : triggers when @Validated fails
	// ¡Esta excepción informa el resultado de violaciones de restricciones!
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Constraint Violation", details);

		return ResponseEntityBuilder.build(err);
	}

	// handleResourceNotFoundException : triggers when there is not resource with
	// the specified ID in BDD
	// Manejar la excepción de recurso no encontrado
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Resource Not Found", details);

		return ResponseEntityBuilder.build(err);
	}

	// 404
	// handleNoHandlerFoundException : triggers when the handler method is invalid
	// ¡Ahora podemos manejar NoHandlerFoundExceptioncomo cualquier otra excepción!
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Method Not Found", details);

		return ResponseEntityBuilder.build(err);

	}

	// 500
	// Definir un manejador de excepciones global nos permite lidiar con todas
	// aquellas excepciones que no tienen manejadores específicos.
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred", details);

		return ResponseEntityBuilder.build(err);

	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();
		
		logger.info(error);
		//
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());

		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				details);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";

		List<String> details = new ArrayList<String>();
		details.add(error);

		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				details);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// 405
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		List<String> details = new ArrayList<String>();
		details.add(builder.toString());
		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED,
				ex.getLocalizedMessage(), details);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// 500
	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<Object> handle500s(final RuntimeException ex, final WebRequest request) {
		log.error("500 Status Code", ex);

		List<String> details = new ArrayList<String>();
		details.add(ex.toString());
		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), details);

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	// 403
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleEverything(final AccessDeniedException ex, final WebRequest request) {
		logger.error("403 Status Code", ex);

		List<String> details = new ArrayList<String>();
		details.add(ex.toString());
		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.FORBIDDEN, ex.getLocalizedMessage(),
				details);

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	// 409
	@ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, ConflictException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
		log.warn("Conflict: {}", ex.getMessage());
		List<String> details = new ArrayList<String>();
		details.add(ex.toString());
		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT, ex.getLocalizedMessage(),
				details);

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	// 415
	@ExceptionHandler({ InvalidMimeTypeException.class, InvalidMediaTypeException.class })
	protected ResponseEntity<Object> handleInvalidMimeTypeException(final IllegalArgumentException ex,
			final WebRequest request) {
		log.warn("Unsupported Media Type: {}", ex.getMessage());
		List<String> details = new ArrayList<String>();
		details.add(ex.toString());
		final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				ex.getLocalizedMessage(), details);

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
	}

	// UTIL
//	private ValidationErrorDTO processFieldErrors(final List<FieldError> fieldErrors) {
//		final ValidationErrorDTO dto = new ValidationErrorDTO();
//		log.error("UTIL");
//		for (final FieldError fieldError : fieldErrors) {
//			final String localizedErrorMessage = fieldError.getDefaultMessage();
//			dto.addFieldError(fieldError.getField(), localizedErrorMessage);
//		}
//		return dto;
//	}

//	private ResponseEntity<Object> message(final HttpStatus httpStatus, final Exception ex) {
//		log.info("\n\n ApiError messageApiError message");
//		final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
//		final String devMessage = ex.getClass().getSimpleName();
//
//		List<String> details = new ArrayList<String>();
//		details.add(message);
//		details.add(devMessage);
//		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Invalid JSON", details);
//
//		return ResponseEntityBuilder.build(err);
//	}

    /*
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class })
    public final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        log.info("Bad Request: {}", ex.getLocalizedMessage());
        log.debug("Bad Request: ", ex);
        
        List<String> details = new ArrayList<String>();
		details.add(ex.toString());
        final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), details);
        
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
    	log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    // 404
    @ExceptionHandler({ EntityNotFoundException.class, EntityNotFoundException.class, ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        log.warn("Not Found: {}", ex.getMessage());

        List<String> details = new ArrayList<String>();
		details.add(ex.toString());
        final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), details);
        
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
  @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class })
    public final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        log.info("Bad Request: {}", ex.getLocalizedMessage());
        log.debug("Bad Request: ", ex);
        List<String> details = new ArrayList<String>();
    		details.add(ex.toString());
          final ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), details);
            
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    */
}
