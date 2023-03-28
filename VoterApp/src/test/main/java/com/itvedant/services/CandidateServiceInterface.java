package com.itvedant.services;

import java.util.List;

import com.itvedant.models.Candidate;

public interface CandidateServiceInterface {

	// 1. Add Candidate
	public void addCandidate(Candidate newCandidate);

	// 2. Get All Candidates
	public List<Candidate> getCandidates();

	// 3. Delete Candidate
	public void deleteCandidate(int id);

	// 4 . Increment Vote
	public void incrementVote(String candidateEmail);
}
