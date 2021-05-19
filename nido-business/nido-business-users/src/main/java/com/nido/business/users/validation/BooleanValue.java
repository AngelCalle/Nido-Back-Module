package com.nido.business.users.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = BooleanValueValidator.class)
@Documented
public @interface BooleanValue {

	String message() default "no es un valor Boolean valido, true | false";

	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}