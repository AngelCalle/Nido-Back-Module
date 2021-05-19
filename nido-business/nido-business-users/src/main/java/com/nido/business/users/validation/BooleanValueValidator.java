package com.nido.business.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BooleanValueValidator implements ConstraintValidator<BooleanValue, Boolean> {

	@Override
	public boolean isValid(Boolean value, ConstraintValidatorContext context) {

		if (value == null || Boolean.toString(true) != null || Boolean.toString(false) != null) {
			return true;
		} else {
			return false;
		}

	}
}
