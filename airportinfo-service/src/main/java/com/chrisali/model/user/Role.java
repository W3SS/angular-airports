package com.chrisali.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@EqualsAndHashCode(exclude="users")
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = -2176776600079232552L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	private final String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
	private Set<User> users;
	
	public Role() { name = RoleType.FREE.toString().toLowerCase(); }

	@Override
	public String getAuthority() { return name;	}
}
