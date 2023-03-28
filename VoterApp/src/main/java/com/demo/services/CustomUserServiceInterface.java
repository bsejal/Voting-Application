package com.demo.services;

import java.util.List;

import com.demo.models.CustomUser;

public interface CustomUserServiceInterface {

	// 1. Add User
	public void addUser(CustomUser newUser);
	
	// 2. Add Admin
	public void addAdmin(CustomUser newAdmin);

	// 3. Get Custom Users
	public List<CustomUser> getCustomUsers();

	// 4. Update Candidate name , hasVoted
	public void submitVote(CustomUser loggedinuser, String candidateEmail);
	
	
}
