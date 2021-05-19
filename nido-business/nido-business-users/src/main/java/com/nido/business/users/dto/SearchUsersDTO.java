package com.nido.business.users.dto;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.nido.business.users.validation.BooleanValue;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SearchUsersDTO {

	@Size(min = 2, max = 3, message = "language is String, required length between 2 and 3")
	public String language;

	@BooleanValue
	public Boolean cookiesPolicy;

	@BooleanValue
	public Boolean isActive;

	@BooleanValue
	public Boolean locked;

	@BooleanValue
	public Boolean disable;

}
