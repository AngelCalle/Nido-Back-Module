package com.nido.common.users.models;

import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Tabla destinada a gestionar los roles de los usuarios en la aplicación,
 * un mismo usuario podrá tener varios roles, con estos se gestiona las
 * diferentes acciones que podrá realizar el usuario.
 * @author Sr.Cekas
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "Role")
@Table(name = "roles")
public class Role implements Serializable {

	@Id
	@NotNull(message = "id is @NotNull")
	@NotEmpty(message = "id is @NotEmpty")
	@NotBlank(message = "id is @NotBlank")
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="ROLE_ID_GENERATOR")
	private Long id;

	@NotNull(message = "name is @NotNull")
	@NotEmpty(message = "name is @NotEmpty")
	@NotBlank(message = "name is @NotBlank")
	@Column(name = "name", unique = true, length = 30, nullable = false)
	@Size(min = 3, max = 30, message = "name is required length between 3 and 30")
	private String name;

	@NotNull(message = "descriptions is @NotNull")
	@NotEmpty(message = "descriptions is @NotEmpty")
	@NotBlank(message = "descriptions is @NotBlank")
	@Column(name = "descriptions", length = 100)
	@Size(min = 3, max = 100, message = "descriptions is required length between 3 and 100")
	private String descriptions;
	
	//mappedBy = "roles", 
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
	private UserNido userNido;
	
	// Sobre escribo el metodo equals
	// Para poder eliminar un rol, el remove en una lista/ArrayList
	// va a buscar el rol dentro de la lista y va a preguntar si es igual
	// si es asi lo elimina, para eso el la entity Role se tiene
	@Override
	public boolean equals(Object obj) {
		
		// Pregunto si la instancia es igual al objeto que se recive por argumento.
		if (this == obj) {
			// Si es igual se elimina el rol.
			return true; 
		}
		
		// Puede que sea una instancia distinta pero el objeto sea el mismo
		// por lo que se tiene que comparar con el id de cada uno.
		// Primero valido que las dos istancias sean del mismo tipo,
		// en caso contrario  retorno false
		if (!(obj instanceof Role)) {
			return false;
		}
		
		// Casteo el objeto para poder compararlo.
		Role r = (Role) obj;
		
		// Compruebo que el id sea distinto de nulo y que sea igual al que recive por parametro,
		// si falla uno de los dos es false y no lo elimina.
		return this.id != null && this.id.equals(r.getId());
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private static final long serialVersionUID = -249480744985340116L;

}
