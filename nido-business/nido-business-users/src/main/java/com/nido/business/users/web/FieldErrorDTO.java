package com.nido.business.users.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class FieldErrorDTO {
	
	private final String field;
    private final String message;

}
