package com.nido.business.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nido.business.users.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByMail(String mail);

	Optional<User> findByCode(String code);

	Optional<User> findByTelephone(String telephone);

	List<User> findByLanguage(String language);

	List<User> findByCookiesPolicy(Boolean cookiesPolicy);

	List<User> findByIsActive(Boolean isActive);

	List<User> findByLocked(Boolean locked);

	List<User> findByDisable(Boolean disable);

	// List<User> findQuery(String language, Boolean cookiesPolicy, Boolean isActive, Boolean locked, Boolean disable);

	// List<User> findUserQuery(String language, Boolean cookiesPolicy, Boolean isActive, Boolean locked, Boolean disable);

	// Page<Iterable<User>> construct(SearchUsersDTO searchUsersDTO, Pageable pageable); 

    // Page<Iterable<User>> caramelo(SearchUsersDTO searchUsersDTO, Pageable pageable) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException; 
	
}