package com.nido.business.users.mapper;


import org.springframework.stereotype.Component;

import com.nido.business.users.dto.UserDTO;
import com.nido.business.users.model.User;

@Component("userMapper")
public class UserMapper {

	public static User DtoToEntity(UserDTO userDTO) {
			return new User()
				.setUserName(userDTO.getUserName())
				.setName(userDTO.getName())
				.setSurNames(userDTO.getSurNames())
				.setTelephone(userDTO.getTelephone())
				.setMail(userDTO.getMail())
				.setLanguage(userDTO.getLanguage())
				.setPassword(userDTO.getPassword())
				.setLastAccessIp(userDTO.getLastAccessIp());
	}

	public static UserDTO EntityToDto(User user) {
		return new UserDTO()
				.setUserName(user.getUserName())
				.setName(user.getName())
				.setSurNames(user.getSurNames())
				.setTelephone(user.getTelephone())
				.setMail(user.getMail())
				.setLanguage(user.getLanguage())
				.setPassword(user.getPassword())
				.setLastAccessIp(user.getLastAccessIp());
	}

}
