package com.nido.business.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SearchUserDTO {

	private Long id;
	private String code;
	private String telephone;
	private String mail;

}
