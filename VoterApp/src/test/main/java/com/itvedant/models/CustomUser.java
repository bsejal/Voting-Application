package com.itvedant.models;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "CUSTOM_USER")
public class CustomUser implements UserDetails{

	/* Fields */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String passcode;
	
	@Column
	private String role;
	
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hasVoted;
	
	@Column
	private String candidateEmail;

	
	
	
	
	/* Constructors */
	
	public CustomUser(int id, String name, String email, String passcode, String role, boolean hasVoted,
			String candidateEmail)
	{
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.passcode = passcode;
		this.role = role;
		this.hasVoted = hasVoted;
		this.candidateEmail = candidateEmail;
	}


	public CustomUser(){}

	
	

	/* Getter & Setter */
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getEmail()
	{
		return email;
	}


	public void setEmail(String email)
	{
		this.email = email;
	}


	public String getPasscode()
	{
		return passcode;
	}


	public void setPasscode(String passcode)
	{
		this.passcode = passcode;
	}


	public String getRole()
	{
		return role;
	}


	public void setRole(String role)
	{
		this.role = role;
	}


	public boolean isHasVoted()
	{
		return hasVoted;
	}


	public void setHasVoted(boolean hasVoted)
	{
		this.hasVoted = hasVoted;
	}


	public String getCandidateEmail()
	{
		return candidateEmail;
	}


	public void setCandidateEmail(String candidateEmail)
	{
		this.candidateEmail = candidateEmail;
	}


	@Override
	public String toString()
	{
		return "CustomUser [id=" + id + ", name=" + name + ", email=" + email + ", passcode=" + passcode + ", role="
				+ role + ", hasVoted=" + hasVoted + ", candidateEmail=" + candidateEmail + "]";
	}


	/* Auth methods */
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		HashSet<SimpleGrantedAuthority> roles = new HashSet<>();		
		roles.add(new SimpleGrantedAuthority(this.role));		
		return roles;
	}


	@Override
	public String getPassword()
	{
		return this.passcode;
	}


	@Override
	public String getUsername()
	{
		return this.email;
	}


	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}


	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}


	@Override
	public boolean isEnabled()
	{
		return true;
	}
	
	
	
	
	
	
	
	
}
