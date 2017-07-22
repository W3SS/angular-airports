package com.chrisali.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Base representaion of a user with fields needed by Spring Security for authentication 
 * 
 * @author Christopher Ali
 *
 */
@Data
@Table(name = "users")
@Entity
public class BaseUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
	@JsonIgnore
	private Long version;
	
	@NotNull
	@Length(min = 5, max = 30)
	private String username;
	
	@NotNull
	@Column(name = "password", length = 75)
	private String password;
	
	private boolean isEnabled;

	public BaseUser() {}
}