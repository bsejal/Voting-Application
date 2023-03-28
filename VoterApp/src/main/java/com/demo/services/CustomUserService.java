package com.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.exceptions.CustomUserDBException;
import com.demo.models.CustomUser;
import com.demo.repositories.CustomUserRepo;

@Service
public class CustomUserService implements CustomUserServiceInterface, UserDetailsService{

	@Autowired
	CustomUserRepo repo;
	
	@Override
	public void addUser(CustomUser newUser)
	{
		Optional<CustomUser> user = repo.findByEmail(newUser.getEmail());
		 
		if(user.isPresent())
		{
			throw new CustomUserDBException("User Already Exists!");
		}
		
		newUser.setHasVoted(false);
		newUser.setCandidateEmail(null);
		newUser.setRole("ROLE_USER");
		repo.save(newUser);
		
	}

	@Override
	public void addAdmin(CustomUser newAdmin)
	{
		Optional<CustomUser> admin = repo.findByEmail(newAdmin.getEmail());
		 
		if(admin.isPresent())
		{
			throw new CustomUserDBException("Admin Already Exists!");
		}
		
		newAdmin.setHasVoted(false);
		newAdmin.setCandidateEmail(null);
		newAdmin.setRole("ROLE_ADMIN");
		repo.save(newAdmin);
		
	}

	@Override
	public List<CustomUser> getCustomUsers()
	{
		return repo.findAll();
	}

	@Override
	public void submitVote(CustomUser loggedinuser, String candidateEmail)
	{
		loggedinuser.setHasVoted(true);
		loggedinuser.setCandidateEmail(candidateEmail);
		System.out.println(loggedinuser);
		
		repo.save(loggedinuser);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return repo.findByEmail(username).get();
	}

	
}
