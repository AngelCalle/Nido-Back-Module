package com.nido.business.users.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nido.business.users.dto.PageableDTO;
import com.nido.business.users.dto.RoleDTO;
import com.nido.business.users.exceptions.ResourceNotFoundException;
import com.nido.business.users.mapper.RoleMapper;
import com.nido.business.users.model.Role;
import com.nido.business.users.services.RoleService;
import com.nido.business.users.web.ApiResponse;



@RestController
@Validated
@RequestMapping("/roles")
public class RoleControllers implements Serializable {

	private static final long serialVersionUID = -1291119471968889799L;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	Validator validator;
	
	// Todos los Roles.
	@GetMapping
	public ApiResponse<List<Role>> getAllRole() {
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Lista de roles",
				roleService.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Role> getById(@PathVariable("id") @Min(1) Long id) {
		
		Role role = roleService.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("No Role with ID : " + id));

		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Role con Id: " + id.toString(),
				role);
		
	}

	
	// Todos los usuarios Paginados
	@PostMapping("/getAllPage")
	public ResponseEntity<Page<Role>> getAllPage(PageableDTO<RoleDTO> pageableDTO) {
		return new ResponseEntity<Page<Role>>(roleService.getAllPage(pageableDTO), HttpStatus.OK);
	}
		
	// Crear Role.
	@PostMapping(value="/")
	public ApiResponse<Role> postCreateUser(@Valid @RequestBody RoleDTO roleDTO) {

		Role roler = RoleMapper.DtoToEntity(roleDTO);
		
		Role newRole = roleService.save(roler);
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.CREATED,
				HttpStatus.CREATED.value(),
				"Role creado con Id: " + newRole.getId().toString(),
				newRole);
		
	}
	
	// Actualizar Role.
	@PutMapping(value="/{id}")
	public ApiResponse<Role> updateUser(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody RoleDTO roleDTO) {

		Role roler = roleService.findById(id)
                					.orElseThrow(()->new ResourceNotFoundException("No Role with ID: " + id));
		
		Role newRole = RoleMapper.DtoToEntity(roleDTO);
		newRole.setId(roler.getId());
		
		Role updateRole = roleService.update(id, newRole);
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.CREATED,
				HttpStatus.CREATED.value(),
				"Role actualizado pos su Id: " + updateRole.getId().toString(),
				updateRole);			
	}

	// Eliminar Role.
	@DeleteMapping(value="/{id}")
	public ApiResponse<String> deleteEmployee( @PathVariable("id") @Min(1) Long id) {
		Role roler = roleService.findById(id)
				                    .orElseThrow(()->new ResourceNotFoundException("No Role with ID: " + id));

		roleService.delete(roler.getId());
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Role con Id: " + id.toString() + " eliminado",
				"");
		
	}
	
	@GetMapping("name/{name}")
	public ApiResponse<Role> findByName(@PathVariable("name") String name) {
		
		Role role = roleService.findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("No Role with roler: " + name));
				
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Role pos su name: " + role.getName().toString(),
				role);
		
	}
	
}
