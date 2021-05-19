package com.nido.business.users.dto;

import java.util.List;

import javax.persistence.Lob;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserDTO {

	private String userName;
	private String name;
	private String surNames;
	private String telephone;
	private String mail;
	private String language;
	private String password;
	private String lastAccessIp;
	private List<RoleDTO> role;
	private byte[] profileImage;

}
