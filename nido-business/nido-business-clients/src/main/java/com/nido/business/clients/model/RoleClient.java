package com.nido.business.clients.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "RoleClient")
@Table(name = "roles_clients")
public class RoleClient implements Serializable {

	private static final long serialVersionUID = -14588511995644573L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "role_name", unique = true, length = 20, nullable = false)
    private String roleName;

    @Column(name = "description", length = 100)
    private String description;

}
