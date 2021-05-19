package com.nido.common.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nido.common.api.models.ApiError;


@ControllerAdvice
public class CustomControllerAdvice {
	
	private static final Logger log = LoggerFactory.getLogger(CustomControllerAdvice.class);
	
	// Cuando se recibe un json con un formato incorrecto crea la respuesta de error.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(Exception exception) {        
        
        
    	List<String> errors = new ArrayList<String>();
    	errors.add(exception.getCause() + " : " + exception.getLocalizedMessage());

    	//log.info("\n\n" + exception.getLocalizedMessage().toString() + "\n\n");
    	//log.info("\n\n" + exception.getMessage().toString() + "\n\n");
    	//log.info("\n\n" + exception.getCause().toString() + "\n\n");
    	//log.info("\n\n" + exception.getClass().toString() + "\n\n");
    	//log.info("\n\n" + exception.getStackTrace().toString() + "\n\n");
    	//log.info("\n\n" + exception.getSuppressed().toString() + "\n\n");
    	//log.info(exception.getSuppressed().toString());
    	
    	log.info("\n\n"  + "ssssssssssssssssssss");
    	Integer indexInit = exception.getCause().toString().indexOf(":") + 2;
    	Integer indexFinit = exception.getCause().toString().indexOf("[") - 1;
    	String cadena = exception.getCause().toString().substring(indexInit, indexFinit);
    	
    	//log.info("\n\n" + exception.getCause().toString().indexOf("[") );
    	
    	
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setErrors(errors);
        apiError.setError(cadena);
        apiError.setMessage("mensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasnemensoasne");

 // com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.lang.Boolean`
 // from String "Caramelo": only "true" or "false" recognized at [Source: (PushbackInputStream); line: 3, column: 22]
 // (through reference chain: com.example.demo.controller.ObjectFilter["cookiesPolicy"])
        return new ResponseEntity<ApiError>(apiError, null, HttpStatus.BAD_REQUEST);
    }
    
}
