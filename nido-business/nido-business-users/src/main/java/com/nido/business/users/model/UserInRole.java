package com.nido.business.users.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {""})
@Entity(name = "UserInRole")
@Table(name = "user_in_role", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}, name = "uniq_user_role"))
public class UserInRole implements Serializable {
	
	private static final Logger log = LoggerFactory.getLogger(UserInRole.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="USER_IN_ROLE_ID_GENERATOR")
	//@SequenceGenerator(name="USER_IN_ROLE_ID_GENERATOR", sequenceName="USER_IN_ROLE_SEQ", allocationSize = 0)
	@Column(name = "id", unique = true, nullable = false)
	@NotNull(message = "id is @NotNull")
	@NotEmpty(message = "id is @NotEmpty")
	@NotBlank(message = "id is @NotBlank")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//	@NotNull(message = "user_id is Required")
	private User userId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
//	@NotNull(message = "role_id is Required")
	private Role roleId;

	// Fecha de concesión entre el usuario y el rol.
	//@NotNull(message = "grant_date is Required")
	@Column(name = "grant_date", length = 20, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	@Size(min = 13, max = 20, message = "grant_date is required length between 13 and 20")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	//@Generated(GenerationTime.ALWAYS) 
	private Date grantDate= new Date();

	// Fecha valida de dicho rol para el usuario.
//	@NotNull(message = "valid_maximum_date is Required")
	@Column(name = "valid_maximum_date", length = 20, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	@Size(min = 13, max = 20, message = "valid_maximum_date is required length between 13 and 20")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date validMaximumDate= new Date();

	@PrePersist
	public void prePersist() {
		grantDate = new Date();
		validMaximumDate = new Date();
	}

	private static final long serialVersionUID = -7255441428675687293L;

}