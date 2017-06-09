package com.chrisali.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisali.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}
