package com.itvedant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itvedant.models.Candidate;

public interface CandidateRepo extends JpaRepository<Candidate, Integer>{

	// 1. Find Candidate by Email	
	public Optional<Candidate> findByEmail(String email);
}
