package com.nido.business.users.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.nido.business.users.dto.SearchUsersDTO;
import com.nido.business.users.dto.UserDTO;
import com.nido.business.users.model.Avatar;
import com.nido.business.users.model.User;
import com.nido.business.users.repository.UserRepository;

@Service
public class UserService implements IUser {

	@Autowired
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepository userRepository;
		
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {

		// Recojo todos los usuarios para obtener el numero de usuarios existente.
		List<User> users = userRepository.findAll();

		// Crea el id para el nuevo usuario
		Long idLong = (long) users.size() + 1;

		// Crea el objeto del nuevo usuario (Algunos valores se crean por defecto en la
		// clase user)
		User newUser = new User();

		// Añade el id creado
		newUser.setId(idLong);

		// TODO: crear un algoritmo para la creacion del id
		newUser.setCode("ND" + idLong.toString());

		// Agrega los valores que se reciven
		newUser.setUserName(user.getUserName());
		newUser.setName(user.getName());
		newUser.setSurNames(user.getSurNames());
		newUser.setTelephone(user.getTelephone());
		newUser.setMail(user.getMail());
		newUser.setLanguage(user.getLanguage());
		newUser.setPassword(user.getPassword());
		newUser.setLastAccessIp(user.getLastAccessIp());

		return userRepository.save(newUser);
	}
	
	@Override
	public User update(Long userId, User user) {

		Optional<User> result =  userRepository.findById(userId);
		// Si existe.
		if (result.isPresent()) {
						
			User newUser = result.get();
			User editUset = new User();
			
			// Agrega los valores que se reciven
			editUset.setUserName(user.getUserName());
			editUset.setName(user.getName());
			editUset.setSurNames(user.getSurNames());
			editUset.setTelephone(user.getTelephone());
			editUset.setMail(user.getMail());
			editUset.setLanguage(user.getLanguage());
			editUset.setPassword(user.getPassword());
			editUset.setLastAccessIp(user.getLastAccessIp());
			editUset.setRoles(user.getRoles());
			
			editUset.setId(newUser.getId());
			editUset.setCode(newUser.getCode());
			editUset.setCreatedOnDate(newUser.getCreatedOnDate());
			editUset.setCookiesPolicy(newUser.getCookiesPolicy());
			editUset.setPassword(newUser.getPassword());
			editUset.setPasswordDateUpdate(newUser.getPasswordDateUpdate());
			editUset.setConfirmationOfRegistration(newUser.getConfirmationOfRegistration());
			editUset.setAttempts(newUser.getAttempts());
			editUset.setIsActive(newUser.getIsActive());
			editUset.setResetToken(newUser.getResetToken());
			editUset.setLastLoginDate(newUser.getLastLoginDate());
			//editUset.setLocked(newUser.getLocked());
			editUset.setLockedDate(newUser.getLockedDate());
			editUset.setLockedReason(newUser.getLockedReason());
			editUset.setDisable(newUser.getDisable());
			editUset.setDisableDate(newUser.getDisableDate());
	
			editUset.setAvatar(newUser.getAvatar());
			
			return userRepository.save(editUset);
		//} else if (result.isEmpty()) {	
		} else if (result.empty() != null) {	
			// Si no existe indico que no existe.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User id %d doesn´t exists", userId));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User id %d doesn´t exists", userId));
		}
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	// Código para mi servicio.
	// private List<User> users = new ArrayList<>();

	@Transactional(readOnly = true)
	public Page<User> getAllPage(int page, int size) {
		return userRepository.findAll(PageRequest.of(page, size));
	}
	
	public User registerFoto(UserDTO userDTO) {
		
		// Recojo todos los usuarios para obtener el numero de usuarios existente.
		List<User> users = userRepository.findAll();

		// Crea el id para el nuevo usuario
		Long idLong = (long) users.size() + 1;

		// Crea el objeto del nuevo usuario (Algunos valores se crean por defecto en la
		// clase user)
		User newUser = new User();

		// Añade el id creado
		newUser.setId(idLong);

		// TODO: crear un algoritmo para la creacion del id
		newUser.setCode("ND" + idLong.toString());

		// Agrega los valores que se reciven
		newUser.setUserName(userDTO.getUserName());
		newUser.setName(userDTO.getName());
		newUser.setSurNames(userDTO.getSurNames());
		newUser.setTelephone(userDTO.getTelephone());
		newUser.setMail(userDTO.getMail());
		newUser.setLanguage(userDTO.getLanguage());
		newUser.setPassword(userDTO.getPassword());
		newUser.setLastAccessIp(userDTO.getLastAccessIp());

	 
		if (userDTO.getProfileImage() != null) {
			Avatar avatar = new Avatar();
			//avatar.setId(idLong);
			avatar.setProfileImage(userDTO.getProfileImage());			
			newUser.setAvatar(avatar);
			//log.info("\n\n\n" + "ddddddddddddddddddddddddd" + newUser.getAvatar().getProfileImage() + "\n\n\n");
		}
		
		return userRepository.save(newUser);
	}
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);



	/**
	 * { "userName": "Andadsgel", "name": "Andadsgel", "surNames": "Andadsgel",
	 * "telephone": "11111441111", "mail": "Andadsgel@Andadsgel.com", "language":
	 * "es", "password": "alksdjfadf", "lastAccessIp": "111.111.1" }
	 */
	public User register(User user) {
		// Recojo todos los usuarios para obtener el numero de usuarios existente.
		List<User> users = userRepository.findAll();
		// Crea el id para el nuevo usuario
		Long idLong = (long) users.size() + 1;
		// Crea el objeto del nuevo usuario (Algunos valores se crean por defecto en la
		// clase user)
		User newUser = new User();

		// Añade el id creado
		newUser.setId(idLong);

		// TODO: crear un algoritmo para la creacion del id
		newUser.setCode("ND" + idLong.toString());

		// Agrega los valores que se reciven
		newUser.setUserName(user.getUserName());
		newUser.setName(user.getName());
		newUser.setSurNames(user.getSurNames());
		newUser.setTelephone(user.getTelephone());
		newUser.setMail(user.getMail());
		newUser.setLanguage(user.getLanguage());
		newUser.setPassword(user.getPassword());
		newUser.setLastAccessIp(user.getLastAccessIp());

		return userRepository.save(newUser);
	}


	/**
	 * { "language": "ES", "cookiesPolicy": true, "isActive": true, "locked": false,
	 * "disable": false }
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */

	@Transactional(readOnly = true)
	public Page<Iterable<User>> construct(SearchUsersDTO u, Pageable pageable) {		

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);

		List<Predicate> predicates = new ArrayList<>();
		
		if (u.getLanguage() != null) {
			predicates.add(cb.like(cb.upper(root.get("language")), (String) u.getLanguage().toUpperCase()));
		}
		if (u.getCookiesPolicy() != null) {
			predicates.add(cb.equal(root.get("cookiesPolicy"), (Boolean) u.getCookiesPolicy()));
		}
		if (u.getIsActive() != null) {
			predicates.add(cb.equal(root.get("isActive"), (Boolean) u.getIsActive()));
		}
		if (u.getLocked() != null) {
			predicates.add(cb.equal(root.get("locked"), (Boolean) u.getLocked()));
		}
		if (u.getDisable() != null) {
			predicates.add(cb.equal(root.get("disable"), (Boolean) u.getDisable()));
		}

		if (predicates.isEmpty()) {
			cr.select(root);
		} else {
			cr.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		}

		String nameColumn = null;
		String sortValue = null;
		Direction direction = null;

		// Obtiene los parámetros de ordenación, columna y dirección.
		if (pageable.getSort().toString() != "UNSORTED") {
			List<Sort> list = Arrays.asList(pageable.getSort());
			for (Sort map : list) {
				for (Order key : map.toSet()) {
					sortValue = key.getProperty();
					direction = key.getDirection();
				}
			}

			if (sortValue != null) {
				User user = new User();
				nameColumn = searchColumn(user, sortValue);
			}
		}

		List<User> listT = null;
		List<User> result = createQuery(listT, cb, cr, root, pageable, nameColumn, direction);

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cb.count(countQuery.from(User.class)));

		Root<User> booksRootCount = countQuery.from(User.class);
		countQuery.select(cb.count(booksRootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		Long count = em.createQuery(countQuery).getSingleResult();

		Page<Iterable<User>> result1 = new PageImpl(result, pageable, count);
		return result1;
	}
	
	@Transactional(readOnly = true)
	public Page<Iterable<User>> caramelo(SearchUsersDTO u, Pageable pageable) throws IllegalArgumentException, IllegalAccessException {		
		//https://jarroba.com/reflection-en-java/
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);

		List<Predicate> predicates = new ArrayList<>();
		
		Class clase = u.getClass();
		
		Field[] campos = clase.getDeclaredFields();
		cr = addConditions(u, cb, cr, root, campos, predicates);
		
		String nameColumn = null;
		String sortValue = null;
		Direction direction = null;
		// Obtiene los parámetros de ordenación, columna y dirección.
		if (pageable.getSort().toString() != "UNSORTED") {
			List<Sort> list = Arrays.asList(pageable.getSort());
			for (Sort map : list) {
				for (Order key : map.toSet()) {
					sortValue = key.getProperty();
					direction = key.getDirection();
				}
			}

			if (sortValue != null) {
				User user = new User();
				nameColumn = searchColumn(user, sortValue);
				
			}
		}
		
		List<User> listT = null;
		List<User> result = createQuery(listT, cb, cr, root, pageable, nameColumn, direction);
		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cb.count(countQuery.from(User.class)));

		Root<User> booksRootCount = countQuery.from(User.class);
		countQuery.select(cb.count(booksRootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		Long count = em.createQuery(countQuery).getSingleResult();

		Page<Iterable<User>> result1 = new PageImpl(result, pageable, count);
		return result1;

	}
	

	// Comprueba que el valor de ordenación sea igual que una columna de la clase
	// que crea la tabla.
	public <T> String searchColumn(Object t, String sortValue) {

		String nameColumn = null;

		Class<? extends T> objetoDeClassConInfoDeMiClase = (Class<? extends T>) t.getClass();
		Field[] todasLasVariablesDeclaradas = objetoDeClassConInfoDeMiClase.getDeclaredFields();

		if (sortValue != null) {
			for (Field variable : todasLasVariablesDeclaradas) {
				if (sortValue.equals(variable.getName())) {
					nameColumn = sortValue;
				}
			}
		}

		if (nameColumn == null) {
			return null;
		} else {
			return nameColumn;
		}
	}

	// Genera la Query en función de las variables de configuración
	public <T> List<T> createQuery(List<T> result, CriteriaBuilder cb, CriteriaQuery<T> cr, Root<T> root,
			Pageable pageable, String nameColumn, Direction direction) {

		if (nameColumn != null) {
			
			if (direction == Direction.ASC) {
				return result = em.createQuery(cr.orderBy(cb.asc(root.get(nameColumn))))
						.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
						.getResultList();
			} else if (direction == Direction.DESC) {
				return result = em.createQuery(cr.orderBy(cb.desc(root.get(nameColumn))))
						.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
						.getResultList();
			} else {
				return result = em.createQuery(cr.orderBy(cb.asc(root.get(nameColumn))))
						.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
						.getResultList();
			}
		} else {
			return result = em.createQuery(cr.select(root)).setFirstResult((int) pageable.getOffset())
					.setMaxResults(pageable.getPageSize()).getResultList();
		}
	}

	public <T> CriteriaQuery<T> addConditions(Object t,  CriteriaBuilder cb, CriteriaQuery<T> cr, Root<T> root, Field[] campos, List<Predicate> predicates) throws IllegalArgumentException, IllegalAccessException {
		for(Field campo : campos) {
			if (campo != null && campo.get(t) != null) {				
				String nombre = campo.getName();
				String valor = campo.get(t).toString();
				String tipo = campo.getType().toString();
				
				
				if (tipo.contains("class java.lang.String")) {
					 predicates.add(cb.like(cb.upper(root.get(nombre)), (String) valor.toUpperCase()));
				}
				
				if (tipo.contains("class java.lang.Boolean")) {
					 predicates.add(cb.equal(root.get(nombre), (Boolean) Boolean.parseBoolean(valor)));
				}
				
			}
		}

		if (predicates.isEmpty()) {
			return cr.select(root);
		} else {
			return cr.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		}		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByCode(String code) {
		return userRepository.findByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByMail(String mail) {
		return userRepository.findByMail(mail);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByTelephone(String telephone) {
		return userRepository.findByTelephone(telephone);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findByLanguage(String language) {
		return userRepository.findByLanguage(language);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findByCookiesPolicy(Boolean cookiesPolicy) {
		return userRepository.findByCookiesPolicy(cookiesPolicy);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findByIsActive(Boolean isActive) {
		return userRepository.findByIsActive(isActive);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByLocked(Boolean locked) {
		return userRepository.findByLocked(locked);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findByDisable(Boolean disable) {
		return userRepository.findByDisable(disable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findQuery(String language, Boolean cookiesPolicy, Boolean isActive, Boolean locked,
			Boolean disable) {
		// return userRepository.findQuery(language, cookiesPolicy, isActive, locked, disable);
		return null;
	}
	
	@Transactional(readOnly = true)
	public List<User> findUserQuery(SearchUsersDTO searchUsers) {
		// return userRepository.findUserQuery(searchUsers.getLanguage(), searchUsers.getCookiesPolicy(), searchUsers.getIsActive(), searchUsers.getLocked(), searchUsers.getDisable());
				return null;
	}



}
