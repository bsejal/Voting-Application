package com.itvedant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.itvedant.services.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	CustomUserService service;
	
	@Bean
	PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(service).passwordEncoder(encoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/registeruser" ).permitAll()
			.antMatchers(HttpMethod.GET, "/registeradmin" ).hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/vote" ).hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/adminhome" ).hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/adduser" ).permitAll()
			.antMatchers(HttpMethod.POST, "/addadmin" ).hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/submitvote" ).hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/" ).permitAll()
			
			.antMatchers(HttpMethod.GET, "/registercandidate" ).hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/addcandidate" ).hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/deletecandidate/**" ).hasAnyRole("ADMIN")
			
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.and()
			.logout().permitAll();
			
			
			
			

	}
}
