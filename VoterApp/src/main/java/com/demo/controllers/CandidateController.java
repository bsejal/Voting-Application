package com.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.exceptions.CandidateDBException;
import com.demo.exceptions.CustomUserDBException;
import com.demo.models.Candidate;
import com.demo.services.CandidateServiceInterface;

@Controller
public class CandidateController {
	
	@Autowired
	CandidateServiceInterface service;
	
	@Autowired
	PasswordEncoder encoder;
	
	/* Web Page Mappings */
	@GetMapping("/registercandidate")
	public String registerCandidate(Model model)
	{
		Candidate candidate = new Candidate();
		
		model.addAttribute("candidate",candidate);
		
		return "registercandidate.html";
	}
	
	/* CRUD Operation Mappings */
	
	@PostMapping("/addcandidate")
	public String addCandidate(@ModelAttribute Candidate newCandidate)
	{
		service.addCandidate(newCandidate);
		return "redirect:/adminhome";
	}
	
	@DeleteMapping("/deletecandidate/{id}")
	public String deleteCandidate(@PathVariable("id") int id)
	{
		service.deleteCandidate(id);
		return "redirect:/adminhome";
	}
	
	/* Exception Handlers */
	
	@ExceptionHandler(CandidateDBException.class)
	public String candidateDBException(CandidateDBException ex, Model model)
	{
		 Map<String,String> errorMap = new HashMap<>();		 
		 errorMap.put("Error", ex.getMessage());		 
		 model.addAttribute("errorMap",errorMap);		 
		 return "error.html";
	}
}
