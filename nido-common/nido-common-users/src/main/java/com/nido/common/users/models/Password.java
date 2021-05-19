package com.nido.common.users.models;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Tabla destinada a gestionar las tres ultimas contraseñas del usuario,
 * también guarda la fecha de actualización de las mismas,
 * con la que se conocerá cual de ellas se a de sustituir en futuras
 * actualizaciones por su antigüedad.
 * @author Sr.Cekas
 *
 */
@Entity
@Table(name = "passwords")
public class Password implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique = true)
	private Long id;

	@NotNull(message = "one_password is Required")
	@Column(name="one_password", length = 60)
	@Size(min = 8, max = 60, message = "one_password is required length between 8 and 60")
	private String onePassword;
	
	@NotNull(message = "one_password_date_update is Required")
	@Column(name="one_password_date_update", length = 20)
	@Size(min = 13, max = 20, message = "one_password_date_update is required length between 13 and 20")
	@Temporal(TemporalType.DATE)
	private Instant onePasswordDateUpdate;
	
	@Column(name="two_password", length = 60)
	@Size(min = 8, max = 60, message = "two_password is required length between 8 and 60")
	private String twoPassword;
	
	@Column(name="two_password_date_update", length = 20)
	@Size(min = 13, max = 20, message = "two_password_date_update is required length between 13 and 20")
	@Temporal(TemporalType.DATE)
	private Instant twoPasswordDateUpdate;
	
	@Column(name="three_password", length = 60)
	@Size(min = 8, max = 60, message = "three_password is required length between 8 and 60")
	private String threePassword;
	
	@Column(name="three_password_date_update", length = 20)
	@Size(min = 13, max = 20, message = "three_password_date_update is required length between 13 and 20")
	@Temporal(TemporalType.DATE)
	private Instant threePasswordDateUpdate;
	
    @OneToOne(mappedBy = "passwords")
    private UserNido user;
	// Inicializa las fechas prara la BBDD, se han de guardar en utc formato iso y milisegundos.
	@PrePersist
	public void prePersist() {
		onePasswordDateUpdate = Instant.now();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOnePassword() {
		return onePassword;
	}

	public void setOnePassword(String onePassword) {
		this.onePassword = onePassword;
	}

	public Instant getOnePasswordDateUpdate() {
		return onePasswordDateUpdate;
	}

	public void setOnePasswordDateUpdate(Instant onePasswordDateUpdate) {
		this.onePasswordDateUpdate = onePasswordDateUpdate;
	}

	public String getTwoPassword() {
		return twoPassword;
	}

	public void setTwoPassword(String twoPassword) {
		this.twoPassword = twoPassword;
	}

	public Instant getTwoPasswordDateUpdate() {
		return twoPasswordDateUpdate;
	}

	public void setTwoPasswordDateUpdate(Instant twoPasswordDateUpdate) {
		this.twoPasswordDateUpdate = twoPasswordDateUpdate;
	}

	public String getThreePassword() {
		return threePassword;
	}

	public void setThreePassword(String threePassword) {
		this.threePassword = threePassword;
	}

	public Instant getThreePasswordDateUpdate() {
		return threePasswordDateUpdate;
	}

	public void setThreePasswordDateUpdate(Instant threePasswordDateUpdate) {
		this.threePasswordDateUpdate = threePasswordDateUpdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -5559864144127299843L;

}
