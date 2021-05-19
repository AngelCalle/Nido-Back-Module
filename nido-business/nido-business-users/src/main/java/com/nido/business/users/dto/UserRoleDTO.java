package com.nido.business.users.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserRoleDTO {

	@NotNull(message = "userId is Required")
	@Min(value = 1, message = "userId is required length min 1")
	private Long userId;
	
	@NotNull(message = "roleId is Required")
	@Min(value = 1, message = "roleId is required length min 1")
	private Long roleId;

}
