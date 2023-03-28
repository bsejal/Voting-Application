package com.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.models.CustomUser;

public interface CustomUserRepo  extends JpaRepository<CustomUser, Integer>{

	// 1. Find Customer by Email	
	public Optional<CustomUser> findByEmail(String email);
}
