package com.nido.business.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RoleDTO {
	
	private Long id;
	private String name;
	private String descriptions;

}
