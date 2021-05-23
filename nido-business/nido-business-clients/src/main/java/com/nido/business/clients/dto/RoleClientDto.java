package com.nido.business.clients.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RoleClientDto {
	
	private Long id;
	private String roleName;
	private String description;

}
