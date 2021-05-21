package org.nido.business.clients.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {""})
@Entity(name = "Client")
@Table(name = "clients")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="CLIENT_ID_GENERATOR")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;;

	@Column(name = "name", unique = true, length = 100, nullable = false)
	private String name;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "company", length = 100)
	private String company;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "client_role",
			joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {"client_id","role_id"}))
	private List<RoleClient> roles;

}
