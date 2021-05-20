package com.nido.common.users.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; // zonedatetime java.time
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Tabla destinada a la gestión de usuarios en la aplicación, la misma tiene
 * relación con barias tablas como roles y passwords
 * Tabla destinada a la gestión de usuarios en la aplicación,
 * la misma tiene relación con barias tablas como roles y passwords
 * @author Sr.Cekas
 */
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {""})
@Entity(name = "User")
@Table(name = "users")
public class UserNido implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="USER_ID_GENERATOR")
	//@SequenceGenerator(name="USER_ID_GENERATOR", sequenceName="USER_SEQ", allocationSize = 0)
	@Column(name = "id", unique = true, nullable = false)
//	@NotNull(message = "id is @NotNull")
	//@NotEmpty(message = "id is @NotEmpty")
	//@NotBlank(message = "id is @NotBlank")
	private Long id;

	// Id que se le mostrara al usuario en caso de ser necesario.
	@Column(name = "code", unique = true, length = 100, nullable = false)
	private String code;

	// Fecha de creación del usuario
//	@NotNull(message = "created_on_date is Required")
	//@Size(min = 13, max = 20, message = "created_on_date is required length between 13 and 20")
	@Column(name = "created_on_date", length = 30, nullable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOnDate;

	// Nombre de usuario / alias.
	@NotNull(message = "user_name is Required")
	@NotEmpty(message = "user_name it cannot be empty")
	@Column(name = "user_name", unique = true, length = 20)
	@Size(min = 5, max = 20, message = "user_name is required length between 5 and 20")
	private String userName;

	// Nombre real.
	@NotNull(message = "name is Required")
	@NotEmpty(message = "name it cannot be empty")
	@Column(name = "name", length = 30)
	@Size(min = 3, max = 30, message = "name is required length between 3 and 30")
	private String name;

	// Apellido real.
	@NotNull(message = "sur_names is Required")
	@NotEmpty(message = "sur_names it cannot be empty")
	@Column(name = "sur_names", length = 60)
	@Size(min = 3, max = 60, message = "sur_names is required length between 3 and 60")
	private String surNames;

	// Numero de telefono.
	@Column(name = "telephone", unique = true, length = 30)
	@Size(min = 5, max = 30, message = "telephone is required length between 3 and 30")
	private String telephone;

	// Mail de reguistro.
	// @Email Que tenga estructura de Email.
	@Email
	@NotNull(message = "mail is Required")
	@NotEmpty(message = "mail it cannot be empty")
	@Column(name = "mail", unique = true, length = 100)
	@Size(min = 6, max = 100, message = "mail isString,  required length between 6 and 100")
	private String mail;

	// Lenguaje de usuario.
	@NotNull(message = "language is Required")
	@NotEmpty(message = "language it cannot be empty")
	@Column(name = "language", length = 3)
	@Size(min = 2, max = 3, message = "language is String, required length between 2 and 3")
	private String language;

	// Politica de cookies aceptada/true denegada/false
	@Column(name = "cookies_policy")
	private Boolean cookiesPolicy;

	// Contraseña.
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "password is Required")
	@NotEmpty(message = "password it cannot be empty")
	@Column(name = "password", length = 60)
	@Size(min = 8, max = 60, message = "password is required length between 8 and 60")
	private String password;

	// Fecha de actualizacion de contraseña.
	//	@NotNull(message = "password_date_update is Required")
	@Column(name = "password_date_update", length = 30)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date passwordDateUpdate;

	// Confirmacion 0/Nada 1/Correo 2/Telefono 3/CorreoYTelefono.
	@Column(name = "confirmation_of_registration")
	// @Pattern(regexp = "^[0-3]{1}", message = "password_date_update length of 1
	// required, value between 0 and 3")
	private Integer confirmationOfRegistration;

	// Intentos de inicio de sesión fallidos. máximo 3
	// @NotNull(message = "attempts is Required")
//	@NotEmpty(message = "attempts it cannot be empty")
	@Column(name = "attempts")
	// @Size(min=1, max=1)
	// @Pattern(regexp = "^[0-3]{1}", message = "attempts length of 1 required,
	// value between 0 and 3")
	private Integer attempts;

	// Para poder activar o desactivar al usuario. Por contraseña bloqueada.
	// @NotNull(message = "is_active is Required")
	// @NotEmpty(message = "is_active it cannot be empty")
	@Column(name = "is_active")
	private Boolean isActive;

	// Restablecer token, valor para generar el enlace de restaurar contraseña.
	// @NotEmpty(message = "reset_token it cannot be empty")
	@Column(name = "reset_token", length = 36)
	// @Size(min = 1, max = 36, message = "reset_token is required length between 1
	// and 36")
	private String resetToken;

	// Fecha de último acceso.
	// @NotNull(message = "last_login_date is Required")
	// @NotEmpty(message = "last_login_date it cannot be empty")
	@Column(name = "last_login_date", length = 20)
	// @Size(min = 13, max = 20, message = "last_login_date is required length
	// between 13 and 20")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	// Ip del ultimo acceso
	// @NotEmpty(message = "last_access_ip it cannot be empty")
	@Column(name = "last_access_ip ", length = 100)
	// @Size(min = 6, max = 100, message = "last_access_ip is required length
	// between 6 and 100")
	private String lastAccessIp;

	// Para bloquear al usuario por algún motivo no referente a la eliminación de
	// usuario
	// Fraude / bot / Dañar la imagen de la compañía...
	// @NotNull(message = "locked is Required")
	// @NotEmpty(message = "locked it cannot be empty")
	@Column(name = "locked")
	private boolean locked;

	// Para bloquear al usuario por algún motivo no referente a la eliminación de
	// usuario
	// Fraude / bot / Dañar la imagen de la compañía...
	// @NotEmpty(message = "locked_reason it cannot be empty")
	@Column(name = "locked_reason ", length = 500)
	// @Size(min = 6, max = 500, message = "lockedReason is required length between
	// 6 and 500")
	private String lockedReason;

	// Para bloquear al usuario por algún motivo no referente a la eliminación de
	// usuario
	// Fraude / bot / Dañar la imagen de la compañía..
	// @NotEmpty(message = "locked_date it cannot be empty")
	@Column(name = "locked_date", length = 20)
	// @Size(min = 13, max = 20, message = "locked_date is required length between
	// 13 and 20")
	// @CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lockedDate;

	// Borrado logico del usuario en la BBDD. Cuando el usuario se da de baja.
	// @NotNull(message = "disable is Required")
	// @NotEmpty(message = "disable it cannot be empty")
	@Column(name = "disable")
	private Boolean disable;

	// Fecha Borrado logico del usuario en la BBDD.
	@Column(name = "disable_date", length = 25)
	// @NotEmpty(message = "disable_date it cannot be empty")
	// @Size(min = 13, max = 25, message = "disable_date is required length between
	// 13 and 25")
	// @CreationTimestamp TODO para que sirve no me deja poner fechas a null
	@Temporal(TemporalType.TIMESTAMP)
	private Date disableDate;

//    @OneToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "passwords_id", referencedColumnName = "id")
//    @Column(name = "passwords")
//    private Password passwords;

	// Roles del usuario. Un usuario muchos roles
	@OneToMany(
			targetEntity = Role.class,
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.ALL,
				    CascadeType.MERGE
		    }
	) // Para que traiga los usuarios con retardo.
	// @JoinTable Tabla intermedia.
	// user_in_role nombre de la nueva tabla
	// inverseJoinColumns Llave foránea de la contraparte.
	@JsonIgnore
	@JoinTable( name = "user_in_role",
				joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
				uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "role_id" })
	)
//	@UniqueConstraint// Para que la combinación de usuario y rol sean únicos, así
	// un usuario no tenga dos veces el mismo rol.
	// Tabla intermedia entre usuarios y roles.
	@JsonBackReference
	private List<Role> roles;
	// https://www.youtube.com/watch?v=kuFBlxwsDFc&ab_channel=MentoriadeSoftware
	// https://www.youtube.com/watch?v=eAQr4G8YBTs&ab_channel=makigas%3Atutorialesdeprogramaci%C3%B3n
	   
/**
 * 			targetEntity = Avatar.class,
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.ALL,
				    CascadeType.MERGE
		    }
 */
	// Avatar del usuario. Un usuario un Avatar

	/**
	 * 	@JsonBackReference
	@PrimaryKeyJoinColumn

	 *     @JoinTable(name = "user_in_avatar", 
      joinColumns = 
        { @JoinColumn(name = "avatar_id", referencedColumnName = "id") },
      inverseJoinColumns = 
        { @JoinColumn(name = "user_id", referencedColumnName = "id") },
      uniqueConstraints = 
      	{ @UniqueConstraint(columnNames = { "user_id", "avatar_id" })})
	 */
	@OneToOne( cascade = CascadeType.ALL)

	//@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)

	 @JoinTable(name = "user_in_avatar", 
     joinColumns = 
       { @JoinColumn(name = "avatar_id", referencedColumnName = "id") },
     inverseJoinColumns = 
       { @JoinColumn(name = "user_id", referencedColumnName = "id") },
     uniqueConstraints = 
     	{ @UniqueConstraint(columnNames = {"user_id", "avatar_id" })})
	@PrimaryKeyJoinColumn
	private Avatar avatar;

	// Ip seria un buen campo a guardar. en una nueva tabla como roles.

	// Inicializa las fechas prara la BBDD, se han de guardar en utc formato iso y
	// milisegundos.
	@PrePersist
	public void prePersist() {
		// TODO: sofisticar este sistema.
		code = "ND" + id.toString();
		createdOnDate = new Date();

		cookiesPolicy = false;
		passwordDateUpdate = new Date();
		confirmationOfRegistration = 0;
		attempts = 0;
		isActive = true;
		resetToken = null;
		lastLoginDate = new Date();

		locked = false;
		lockedReason = null;
		// TODO: esta fecha no se guarda en null;
		lockedDate = null;
		disable = false;
		// TODO: esta fecha no se guarda en null;
		disableDate = null;
	
	}

	// Constructor para inizialicar Role con un array list
	public UserNido() {
		this.roles = new ArrayList<>();
	}

	// Método que devuelve un identificador de la imagen del usuario.
	// get para que lo muestre en el JSON, se serializa.
//	public Integer getFotoHashCode() {
	// Verifico que exista la imagen antes de enviarla y evitar el error.
//		return (this.profileImage != null) ? this.profileImage.hashCode() : null;
//	}

	// Metodo para agregar un unico rol aun usuario
//	public void addRole(Role role) {		
//		if (this.roles.size() == 0) {
//			this.roles.add(role);
//		} else {			
//			for (Role roles : this.roles) {
//				if (roles.getId() != role.getId()) {
//					this.roles.add(role);
//					break;
//				}
//			}
//		}
//	}

	// Metodo para eliminar un rol a un asuario.
	// Para poder eliminar un rol, el remove en una lista/ArrayList
	// va a buscar el rol dentro de la lista y va a preguntar si es igual
	// si es asi lo elimina, para eso el la entity Role se tiene
	// que implementar/sobre escribir el metodo equals.
//	public void removeRole(Role role) {
//		if (this.roles.size() >= 1) {
//			for (Role roles : this.roles) {
//				if (roles.getId() == role.getId()) {
//					this.roles.remove(role);
//					break;
//				}
//			}
//		}
//	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 4002221912401133094L;

}