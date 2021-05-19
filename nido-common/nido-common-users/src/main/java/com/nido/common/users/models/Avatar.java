package com.nido.common.users.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
//Habilitar el mecanismo de auditoría de Spring Data JPA, @CreatedDate, @LastModifiedDate
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties Se usar para ignorar campos durante la serialización y deserialización de JSON.
//Excluye las propiedades createdDate y updatedDate.
//@JsonIgnoreProperties(value = {"uploadDate", "updatedDate"})
@Entity(name = "Avatar")
@Table(name = "avatars")
public class Avatar implements Serializable {
	
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
///	@NotNull(message = "id is @NotNull")
//	@NotEmpty(message = "id is @NotEmpty")
//	@NotBlank(message = "id is @NotBlank")
	private Long id;

	@CreationTimestamp
	@Column(name = "upload_date", length = 20)
//	@NotNull(message = "upload_date is @NotNull")
//	@NotEmpty(message = "upload_date is @NotEmpty")
//	@NotBlank(message = "upload_date is @NotBlank")
	//@Size(min = 13, max = 20, message = "upload_date is required length between 13 and 20")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;

	// Dirección de la imagen del usuario en la BBDD.
	// Foto del usuario.
	@Lob
	// @JsonIgnore El contenido que se guarda es un binario muy grande
	// por lo que no es necesario mostrarlo en el json.
	@JsonIgnore
	//@NotEmpty(message = "profile_image it cannot be empty")
	@Column(name = "profile_image", unique = true)
///	@NotNull(message = "profile_image is @NotNull")
//	@NotEmpty(message = "profile_image is @NotEmpty")
//	@NotBlank(message = "profile_image is @NotBlank")
	//@Size(min = 6, max = 100000, message = "profile_image is required length between 6 and 100")
	private byte[] profileImage;
	
	//@OneToOne(mappedBy = "avatar", cascade = CascadeType.ALL)
	//@OneToOne
//	@JoinColumn(name="user_id")
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
	private UserNido user;
	
	@PrePersist
	public void prePersist() {
		uploadDate = new Date();
	}
	
	
	// Método que devuelve un identificador de la imagen del usuario.
	// get para que lo muestre en el JSON, se serializa.
	public Integer getFotoHashCode() {
		// Verifico que exista la imagen antes de enviarla y evitar el error.
		return (this.profileImage != null) ? this.profileImage.hashCode() : null;
	}

	private static final long serialVersionUID = 4704712774868178669L;

}