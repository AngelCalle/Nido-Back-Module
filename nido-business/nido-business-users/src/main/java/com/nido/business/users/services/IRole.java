package com.nido.business.users.services;

import java.util.List;
import java.util.Optional;

import com.nido.business.users.model.Role;

public interface IRole {
	
	public List<Role> findAll();
	
	public Optional<Role> findById(Long id);
	
	public Role save(Role user);
	
	public Role update(Long userId, Role user);
	
	public void delete(Long id);
	
	public Optional<Role> findByName(String name);

}
