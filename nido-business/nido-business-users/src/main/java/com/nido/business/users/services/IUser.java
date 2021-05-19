package com.nido.business.users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.nido.business.users.model.User;

public interface IUser {
	
	public List<User> findAll();
	
	public Optional<User> findById(Long id);
	
	public User save(User user);
	
	public User update(Long userId, User user);
	
	public void delete(Long id);
	


	public Optional<User> findByCode(String code);

	public Optional<User> findByMail(String mail);

	public Optional<User> findByTelephone(String telephone);

	public List<User> findByLanguage(String language);

	public List<User> findByCookiesPolicy(Boolean cookiesPolicy);

	public List<User> findByIsActive(Boolean isActive);

	public List<User> findByLocked(Boolean locked);

	public List<User> findByDisable(Boolean disable);

	// @Query("select u from User u where u.emailAddress = ?1")
	// User findByEmailAddress(String emailAddress);

	// Funciona con todos los valores
	/**
	 * 
	 * @Query(value = "SELECT u FROM User u WHERE u.language =?1 AND u.cookiesPolicy
	 *              =?2 AND u.isActive =?3 AND u.locked =?4 AND u.disable =?5 ORDER
	 *              BY user_name ASC", nativeQuery = false)
	 * 
	 *              { "language": "es", "cookiesPolicy": true, "isActive": true,
	 *              "locked": false, "disable": false }
	 */
	// @Query(value = "SELECT u FROM User u WHERE u.language IN :?1 u.cookiesPolicy
	// IN :?2 u.isActive IN :?3 u.locked IN :?4 u.disable IN :?5", nativeQuery =
	// true)
	// @Query(value = "SELECT u FROM User u WHERE CONCAT(u.language,
	// u.cookiesPolicy, u.isActive, u.locked, u.disable) LIKE %?1% ORDER BY
	// user_name ASC", nativeQuery = false)
	// Ejemplos para revisar a la hora de realizar un filtro
	@Query(value = "SELECT u FROM User u WHERE  LOWER(u.language) =LOWER(?1) AND u.cookiesPolicy =?2 AND u.isActive =?3 AND u.locked =?4 AND u.disable =?5 ORDER BY user_name ASC", nativeQuery = false)
	public List<User> findQuery(String language, Boolean cookiesPolicy, Boolean isActive, Boolean locked,
			Boolean disable);
	
	// Page<Iterable<User>> construct(SearchUsersDTO searchUsersDTO, Pageable pageable); 

	

}
