package org.nido.business.clients.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import lombok.Data;
//import lombok.experimental.Accessors;
//
//@Data
//@Accessors(chain = true)
//@EntityListeners(AuditingEntityListener.class)
////@JsonIgnoreProperties(value = {""})
//@Entity(name = "Client")
//@Table(name = "clients")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "application_name")
	private String application_name;

	@Column(name = "company")
	private String company;

	/**
	 * Roles are being eagerly loaded here because they are a fairly small
	 * collection of items for this example.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<RoleClient> roles;

}
