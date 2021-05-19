package com.nido.business.users.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nido.business.users.dto.SearchUserDTO;
import com.nido.business.users.dto.SearchUsersDTO;
import com.nido.business.users.dto.UserDTO;
import com.nido.business.users.dto.UserRoleDTO;
import com.nido.business.users.exceptions.ResourceNotFoundException;
import com.nido.business.users.mapper.UserMapper;
import com.nido.business.users.model.Role;
import com.nido.business.users.model.User;
import com.nido.business.users.services.RoleService;
import com.nido.business.users.services.UserService;
import com.nido.business.users.web.ApiResponse;

@RestController
@Validated
@RequestMapping("/users")
public class UserController implements Serializable {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	Validator validator;
	
	// Todos los Usuarios.
	@GetMapping
	public ApiResponse<List<User>> getAllUser() {
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Lista de usuarios",
				userService.findAll());
	}
	
	// Usuario por Id.
	@GetMapping("/{id}")
	public ApiResponse<User> getById(@PathVariable("id") @Min(1) Long id) {
		
		User user = userService.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("No User with ID : " + id));
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Usuario con Id: " + id.toString(),
				user);
	}
	
	// Crear Usuario.
	@PostMapping(value="/")
	public ApiResponse<User> postCreateUser(@Valid @RequestBody UserDTO userDTO) {

		User user = UserMapper.DtoToEntity(userDTO);
		
		User newUser = userService.save(user);
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.CREATED,
				HttpStatus.CREATED.value(),
				"Usuario creado con Id: " + newUser.getId().toString(),
				newUser);
	}
	
	// Actualizar Usuario.
	@PutMapping(value="/{id}")
	public ApiResponse<User> updateUser(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody UserDTO userDTO) {

		User user = userService.findById(id)
                					.orElseThrow(()->new ResourceNotFoundException("No User with ID : " + id));
		
		User newUser = UserMapper.DtoToEntity(userDTO);
		newUser.setId(user.getId());
		
		User updateUser = userService.update(id, newUser);
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.CREATED,
				HttpStatus.CREATED.value(),
				"Usuario actualizado pos su Id: " + updateUser.getId().toString(),
				updateUser);
			
	}
	
	// Eliminar Usuario.
	@DeleteMapping(value="/{id}")
	public ApiResponse<String> deleteEmployee( @PathVariable("id") @Min(1) Long id) {
		User user = userService.findById(id)
				                    .orElseThrow(()->new ResourceNotFoundException("No User with ID : " + id));

		userService.delete(user.getId());
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.OK,
				HttpStatus.OK.value(),
				"Usuario con Id: " + id.toString() + " eliminado",
				"");
		
	}	

	// Todos los usuarios Paginados
	@GetMapping("/allUsersPage")
	public ResponseEntity<Page<User>> getAllPage(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "100") int size) {
		return new ResponseEntity<Page<User>>(userService.getAllPage(page, size), HttpStatus.OK);
	}

	@GetMapping("code/{code}")
	public Optional<User> findByCode(@PathVariable("code") String code) {
		return userService.findByCode(code);
	}

	@GetMapping("mail/{mail}")
	public Optional<User> findByMail(@PathVariable("mail") String mail) {
		return userService.findByMail(mail);
	}

	/**
	 * Con uno de estos valores devuelve un usuario {"id": 1,} {"userId": "user1"}
	 * {"telephone": "111111111"} {"mail": "Angel@Angel.com"}
	 */
	@GetMapping("searchUser")
	public Optional<User> searchUser(@RequestBody SearchUserDTO searchUser) {

		if (searchUser.getId() != null) {
			return userService.findById(searchUser.getId());
		} else if (searchUser.getCode() != null) {
			return userService.findByCode(searchUser.getCode());
		} else if (searchUser.getMail() != null) {
			return userService.findByMail(searchUser.getMail());
		} else if (searchUser.getTelephone() != null) {
			return userService.findByTelephone(searchUser.getTelephone());
		} else {
			return null;
		}

	}

	@GetMapping("language/{language}")
	public List<User> findByLanguage(@PathVariable("language") String language) {
		return userService.findByLanguage(language);
	}

	@GetMapping("cookiesPolicy/{cookiesPolicy}")
	public List<User> findByCookiesPolicy(@PathVariable("cookiesPolicy") Boolean cookiesPolicy) {
		return userService.findByCookiesPolicy(cookiesPolicy);
	}

	@GetMapping("isActive/{isActive}")
	public List<User> findByIsActive(@PathVariable("isActive") Boolean isActive) {
		return userService.findByIsActive(isActive);
	}

	@GetMapping("locked/{locked}")
	public List<User> findByLocked(@PathVariable("locked") Boolean locked) {
		return userService.findByLocked(locked);
	}

	@GetMapping("disable/{disable}")
	public List<User> findByDisable(@PathVariable("disable") Boolean disable) {
		return userService.findByDisable(disable);
	}

	/**
	 * Con uno de estos valores devuelve una lista de usuarios {"language": "es"}
	 * {"cookiesPolicy": true} {"isActive": true} {"locked": true} {"disable":
	 * false}
	 */
	@GetMapping("searchUsers")
	public List<User> searchUser(@RequestBody SearchUsersDTO searchUsers) {

		if (searchUsers.getLanguage() != null) {
			return userService.findByLanguage(searchUsers.getLanguage());
		} else if (searchUsers.getCookiesPolicy() != null) {
			return userService.findByCookiesPolicy(searchUsers.getCookiesPolicy());
		} else if (searchUsers.getIsActive() != null) {
			return userService.findByIsActive(searchUsers.getIsActive());
		} else if (searchUsers.getLocked() != null) {
			return userService.findByLocked(searchUsers.getLocked());
		} else if (searchUsers.getDisable() != null) {
			return userService.findByDisable(searchUsers.getDisable());
		} else {
			return null;
		}

	}

	@GetMapping("searchUsersQuery")
	public List<User> findUserQuery(@RequestBody SearchUsersDTO searchUsers) {
		return userService.findUserQuery(searchUsers);
	}

	// Método crear con foto.
	@PostMapping("/crear")
	// @RequestBody Para obtener la variable del body que llega en la llamada.
	// @Valid Habilita la validación.
	// BindingResult result A través del resultado obtengo los mensajes de error.
	public ResponseEntity<User> crear(@RequestBody User user) {
		// Una vez asignada la imagen reutilizo el método crear.
		// return super.crear(alumno, result);
		return new ResponseEntity<User>(userService.register(user), HttpStatus.OK);
	}



	// Método crear con foto.
	@PostMapping("/crear-con-foto")
	// @RequestBody Para obtener la variable del body que llega en la llamada.
	// @Valid Habilita la validación.
	// BindingResult result A través del resultado obtengo los mensajes de error de las validaciones.
	//@RequestBody @Valid UserDTO userDTO,
	public ResponseEntity<User> crearConFoto(
			@Valid UserDTO userDTO,
			BindingResult result,
			@RequestParam MultipartFile archive) throws IOException {
		
		// Video 3 gestiona los BindingResult de una manera chula.
		// https://www.youtube.com/watch?v=BZl4_LuYhm4&list=PLxy6jHplP3Hi_W8iuYSbAeeMfaTZt49PW&index=9&ab_channel=DigitalLabAcademy
		
		// Valido que venga el archivo.
		if (!archive.isEmpty()) {
			// Si es distinto de vació se lo asigno al alumno.
			// throws IOException obliga a la excepción de método.
			userDTO.setProfileImage(archive.getBytes());			
		}
		
		return new ResponseEntity<User>(userService.registerFoto(userDTO), HttpStatus.OK);
	}

	/**
	 * Con uno de estos valores devuelve una lista de usuarios {"language": "es",
	 * "cookiesPolicy": true, "isActive": true, "locked": true, "disable":
	 * false} @PostMapping("/listUser") public Iterable<User> saveUser(@RequestBody
	 * ObjectFilter u) { return userService.construct(u, page, size); }
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * 
	 */

	@PostMapping("/listUser")
	public Page<Iterable<User>> getAllUserdsPage(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "100") int size,
			@RequestParam(required = false, value = "sort", defaultValue = "name") String sortProperty,
			@RequestParam(required = false, value = "order", defaultValue = "ASC") Sort.Direction direction,
			@RequestBody SearchUsersDTO u) {

		Pageable pageable;
		if (sortProperty != null && !sortProperty.isEmpty()) {
			Sort sort = null;
			if (direction.equals(Direction.ASC)) {
				sort = Sort.by(Sort.Order.asc(sortProperty.replace(" ", "")));
				pageable = PageRequest.of(page, size, sort);
			} else if (direction.equals(Direction.DESC)) {
				sort = Sort.by(Sort.Order.desc(sortProperty.replace(" ", "")));
				pageable = PageRequest.of(page, size, sort);
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sortProperty.replace(" ", "")));
			}
		} else {
			pageable = PageRequest.of(page, size);
		}
		return userService.construct(u, pageable);
	}

	@PostMapping("/caramelo")
	public ResponseEntity<Page<Iterable<User>>> caramelo(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "100") int size,
			@RequestParam(required = false, value = "sort", defaultValue = "name") String sortProperty,
			@RequestParam(required = false, value = "order", defaultValue = "ASC") Sort.Direction direction,
			@Valid @RequestBody SearchUsersDTO u,
			BindingResult result,
			HttpServletResponse response)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, ServletException {

			// log.info("\n\n u " + u + "\n\n");
			// log.info("\n\n result " + result + "\n\n");
			// log.info("\n\n response " + response + "\n\n");

			// Valido antes de guardar que los campos sean correctos.
			if (result.hasErrors()) {
				System.out.println("\n\n" + "caramelo" + "\n\n");
				ResponseEntity<?> ca = this.validar(result);
				log.info("\n\n result.uno " + ca + "\n\n");
			}

			Pageable pageable = pageable(page, size, sortProperty, direction);

			// return userService.caramelo(u, pageable);
			
			Page<Iterable<User>> _customer = userService.caramelo(u, pageable);
			return new ResponseEntity<>(_customer, HttpStatus.CREATED);
	}
	
	public Pageable pageable(int page, int size, String sortProperty, Sort.Direction direction) {
		Pageable pageable;
		if (sortProperty != null && !sortProperty.isEmpty()) {
			Sort sort = null;
			if (direction.equals(Direction.ASC)) {
				sort = Sort.by(Sort.Order.asc(sortProperty.replace(" ", "")));
				pageable = PageRequest.of(page, size, sort);
			} else if (direction.equals(Direction.DESC)) {
				sort = Sort.by(Sort.Order.desc(sortProperty.replace(" ", "")));
				pageable = PageRequest.of(page, size, sort);
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sortProperty.replace(" ", "")));
			}
		} else {
			pageable = PageRequest.of(page, size);
		}
		return pageable;
	}

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private static final long serialVersionUID = -2207615073649531254L;

	// Implemento la respuesta con los mensajes de error,
	// mediante BindingResult result.
	protected ResponseEntity<?> validar(BindingResult result) {
		// Genera un JSON con los mensajes de error.
//		log.info("\n\n result.dos " + result + "\n\n");

		// Obtengo la lista de los errores.
		Map<String, Object> errores = new HashMap<>();

		// Paso los errores detectados al cuerpo de la respuesta.
		result.getFieldErrors().forEach(err -> {
			// log.info("\n\n" + err.getField() + " --El campo: " + err.getField() + ", " +
			// err.getDefaultMessage() + ".");
			// err.getField() retorna el nombre del campo que contiene el error.
			errores.put(err.getField(), "  --El campo: " + err.getField() + ", " + err.getDefaultMessage() + ".");
		});

		// .badRequest() Respuesta tipo 400.
		return ResponseEntity.badRequest().body(errores);

	}

	
	@PostMapping("/addRole")
	public ApiResponse<User> addRole(@Valid @RequestBody UserRoleDTO userRoleDTO) {
		
		User user = userService.findById(userRoleDTO.getUserId())
				.orElseThrow(()->new ResourceNotFoundException("No User with ID: " + userRoleDTO.getUserId()));
		
		Role role = roleService.findById(userRoleDTO.getRoleId())
				.orElseThrow(()->new ResourceNotFoundException("No Role with ID: " + userRoleDTO.getRoleId()));
		
		user.addRole(role);
		
		User updateUser = userService.update( userRoleDTO.getUserId(), user);
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.CREATED,
				HttpStatus.CREATED.value(),
				"Usuario actualizado pos su Id: " + updateUser.getId().toString(),
				updateUser);

	}
	
	@PostMapping("/removeRole")
	public ApiResponse<User> removeRole(@Valid @RequestBody UserRoleDTO userRoleDTO) {
		
		User user = userService.findById(userRoleDTO.getUserId())
				.orElseThrow(()->new ResourceNotFoundException("No User with ID: " + userRoleDTO.getUserId()));
		
		Role role = roleService.findById(userRoleDTO.getRoleId())
				.orElseThrow(()->new ResourceNotFoundException("No Role with ID: " + userRoleDTO.getRoleId()));
		
		user.removeRole(role);
		
		User updateUser = userService.update(userRoleDTO.getUserId(), user);
		
		return new ApiResponse<>(
				LocalDateTime.now(),
				HttpStatus.CREATED,
				HttpStatus.CREATED.value(),
				"Usuario actualizado pos su Id: " + updateUser.getId().toString(),
				updateUser);

	}
	
	
}
