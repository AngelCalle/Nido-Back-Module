package com.nido.business.users.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {""})
@Entity(name = "UserInAvatar")
@Table(name = "user_in_avatar")
//@Table(name = "user_in_avatar", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "avatar_id"}, name = "uniq_user_avatar"))
public class UserInAvatar implements Serializable {

	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//	@NotNull(message = "user_id is Required")
	private User userId;

	@OneToOne
	@JoinColumn(name = "avatar_id", referencedColumnName = "id", nullable = false)
//	@NotNull(message = "avatar_id is Required")
	private Avatar avatarId;

	// Fecha de concesión entre el usuario y el rol.
	@Column(name = "grant_date", length = 20)
	@Size(min = 13, max = 20, message = "grant_date is required length between 13 and 20")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date grantDate;

	@PrePersist
	public void prePersist() {
		grantDate = new Date();
	}

	private static final long serialVersionUID = -1868731478554805763L;

}
