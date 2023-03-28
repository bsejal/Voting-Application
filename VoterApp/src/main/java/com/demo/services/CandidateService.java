package com.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.exceptions.CandidateDBException;
import com.demo.models.Candidate;
import com.demo.repositories.CandidateRepo;

@Service
public class CandidateService implements CandidateServiceInterface{

	@Autowired
	CandidateRepo repo;
	
	@Override
	public void addCandidate(Candidate newCandidate)
	{
		Optional<Candidate> candidate = repo.findByEmail(newCandidate.getEmail());
		 
		if(candidate.isPresent())
		{
			throw new CandidateDBException("Candidate Already Exists!");
		}
		
		newCandidate.setCount(0);
		repo.save(newCandidate);
		
	}

	@Override
	public List<Candidate> getCandidates()
	{
		return repo.findAll();
	}

	@Override
	public void deleteCandidate(int id)
	{
		repo.deleteById(id);
		
	}

	@Override
	public void incrementVote(String candidateEmail)
	{
		Optional<Candidate> candidate = repo.findByEmail(candidateEmail);
		
		candidate.get().setCount(candidate.get().getCount() + 1);
		
		repo.save(candidate.get());
	}

}
