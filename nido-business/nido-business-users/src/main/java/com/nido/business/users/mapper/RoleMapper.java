package com.nido.business.users.mapper;

import org.springframework.stereotype.Component;

import com.nido.business.users.dto.RoleDTO;
import com.nido.business.users.model.Role;

@Component("roleMapper")
public class RoleMapper {
	
	public static Role DtoToEntity(RoleDTO roleDTO) {
		return new Role()
				.setId(roleDTO.getId())
				.setName(roleDTO.getName())
				.setDescriptions(roleDTO.getDescriptions());	
	}

	public static RoleDTO EntityToDto(Role role) {
		return new RoleDTO()
				.setId(role.getId())
				.setName(role.getName())
				.setDescriptions(role.getDescriptions());
	}

}
