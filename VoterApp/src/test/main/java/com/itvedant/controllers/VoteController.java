package com.itvedant.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.itvedant.customannotations.LoggedInUser;
import com.itvedant.exceptions.CustomUserDBException;
import com.itvedant.models.Candidate;
import com.itvedant.models.CustomUser;
import com.itvedant.services.CandidateServiceInterface;
import com.itvedant.services.CustomUserServiceInterface;

@Controller
public class VoteController {
	
	@Autowired
	CustomUserServiceInterface service;
	
	@Autowired
	CandidateServiceInterface candidateService;
	
	@Autowired
	PasswordEncoder encoder;
	
	/* Web Page Mappings */
	@GetMapping("/registeruser")
	public String registerUser(Model model)
	{
		CustomUser user = new CustomUser();
		 
		model.addAttribute("user",user);
		return "registeruser.html";
	}
	
	@GetMapping("/registeradmin")
	public String registerAdmin(Model model)
	{
		CustomUser admin = new CustomUser();
		
	
		model.addAttribute("admin",admin);
		return "registeradmin.html";
	}
	
	@GetMapping("/vote")
	public String vote(Model model, @LoggedInUser CustomUser loggedInUser)
	{	
		
		System.out.println(loggedInUser);
		
		if(loggedInUser.isHasVoted())
		{
			return "alreadyvoted.html";
		}
		
		List<Candidate> candidates = candidateService.getCandidates();		
		CustomUser tempUser = new CustomUser();
		
		model.addAttribute("candidates",candidates);
		model.addAttribute("tempUser", tempUser);
		
		return "vote.html";
	}
	
	
	@GetMapping("/login")
	public String login(Model model)
	{			
		return "login.html";
	}
	
	@GetMapping("/")
	public String index(@LoggedInUser CustomUser loggedInUser)
	{		
		String role = loggedInUser.getRole();
		
		if(role.equals("ROLE_USER"))
		{
			
			if(loggedInUser.isHasVoted())
			{
				return "alreadyvoted.html";
			}
			return "redirect:/vote";
		}
		else if(role.equals("ROLE_ADMIN"))
		{
			return "redirect:/adminhome";		
		}
		return "error.html";
	}
	
	
	@GetMapping("/adminhome")
	public String adminHome(Model model)
	{
		List<Candidate> candidates = candidateService.getCandidates();
		List<CustomUser> customusers = service.getCustomUsers();
		
		model.addAttribute("candidates",candidates);
		model.addAttribute("customusers", customusers);
		
		return "adminhome.html";
	}
	
	
	/* CRUD Operation Mappings */
	
	@PostMapping("/adduser")
	public String addUser(@ModelAttribute("user") CustomUser newUser)
	{
		
		newUser.setPasscode(encoder.encode(newUser.getPasscode()));
		service.addUser(newUser);
		return "redirect:/vote";
	}
	
	@PostMapping("/addadmin")
	public String addAdmin(@ModelAttribute("admin") CustomUser newAdmin)
	{
		newAdmin.setPasscode(encoder.encode(newAdmin.getPasscode()));
		service.addAdmin(newAdmin);
		return "redirect:/adminhome";	
	}
	
	@PutMapping("/submitvote")
	public String submitVote(@ModelAttribute("tempUser") CustomUser tempUser, @LoggedInUser CustomUser loggedinuser )
	{
			String candidateEmail = tempUser.getCandidateEmail();			
			service.submitVote(loggedinuser, candidateEmail);
			
			
			candidateService.incrementVote(candidateEmail);
			
			return "successfullyvoted.html";		
	}
	
	
	
	
	
	
	/* Exception Handling */
	
	@ExceptionHandler(CustomUserDBException.class)
	public String customUserDBException(CustomUserDBException ex, Model model)
	{
		 Map<String,String> errorMap = new HashMap<>();		 
		 errorMap.put("Error", ex.getMessage());		 
		 model.addAttribute("errorMap",errorMap);		 
		 return "error.html";
	}
	
	

}
