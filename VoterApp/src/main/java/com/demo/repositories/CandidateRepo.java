package com.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.models.Candidate;

public interface CandidateRepo extends JpaRepository<Candidate, Integer>{

	// 1. Find Candidate by Email	
	public Optional<Candidate> findByEmail(String email);
}
