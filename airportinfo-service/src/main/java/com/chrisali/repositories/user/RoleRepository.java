package com.chrisali.repositories.user;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisali.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Set<Role> findByUsers_Id(Long id);

	public Role findByName(String name);
}
