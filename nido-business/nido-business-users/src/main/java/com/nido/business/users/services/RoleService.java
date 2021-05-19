package com.nido.business.users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.nido.business.users.dto.PageableDTO;
import com.nido.business.users.dto.RoleDTO;
import com.nido.business.users.model.Role;
import com.nido.business.users.repository.RoleRepository;

@Service
public class RoleService implements IRole {	
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Page<Role> getAllPage(PageableDTO<RoleDTO> pageableDTO) {
		
		if (pageableDTO.getPage() == null) {			
			pageableDTO.setPage(0);
		}
		if (pageableDTO.getSize() == null) {
			pageableDTO.setPage(100);
		}
		
		if (pageableDTO.getSort() != null) {
			// Validar que el campo existe.
			if (pageableDTO.getOrder().equals(Direction.ASC)) {				
				return roleRepository.findAll(PageRequest.of(
						pageableDTO.getPage(),
						pageableDTO.getSize(),
						Sort.by(pageableDTO.getSort().toString()).ascending()));
			} else if (pageableDTO.getOrder().equals(Direction.DESC)) {				
				return roleRepository.findAll(PageRequest.of(
						pageableDTO.getPage(),
						pageableDTO.getSize(),
						Sort.by(pageableDTO.getSort().toString()).descending()));
			} else {
				return roleRepository.findAll(PageRequest.of(
						pageableDTO.getPage(),
						pageableDTO.getSize(),
						Sort.by(pageableDTO.getSort().toString()).ascending()));
			}
		} else {
			return roleRepository.findAll(PageRequest.of(
					pageableDTO.getPage(),
					pageableDTO.getSize()));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}
	
	@Override
	public Role save(Role role) {
		
		Role newRole = new Role();

		newRole.setName(role.getName().toUpperCase());
		newRole.setDescriptions(role.getDescriptions());

		return roleRepository.save(newRole);

	}

	@Override
	public Role update(Long roleId, Role user) {
		Optional<Role> result =  roleRepository.findById(roleId);
		// Si existe.
		if (result.isPresent()) {
			
			Role newRole = result.get();
			Role editRole = new Role();
			
			editRole.setId(newRole.getId());
			editRole.setName(user.getName().toUpperCase());
			editRole.setDescriptions(user.getDescriptions());
			
			return roleRepository.save(editRole);
		} else if (result.empty() != null) {			
			// Si no existe indico que no existe.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn´t exists", roleId));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn´t exists", roleId));
		}
	}

	@Override
	public void delete(Long id) {
		roleRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}

}
