package com.cognixia.jump.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;

import com.cognixia.jump.jwt.JwtRequestFilter;

@Configuration
public class SecurityConfiguration {
	
	// this user details service will manage users in an actual DB
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	
	// Authentication -> who are you?
	@Bean
	protected UserDetailsService userDetailsService() {
		
		return userDetailsService;
	}

	
	// Authorization - what do you want? (what can a user access?)
	@Bean
	protected SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
		
	
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/instructors").permitAll() // anyone can create a user (user sign ups)
				.antMatchers(HttpMethod.GET, "/api/instructors").permitAll()
				.antMatchers("/openapi.html").permitAll()
				
				.antMatchers("/authenticate").permitAll() // anyone can ATTEMPT to create a JWT
		   // if not specified, all other end points need a user login

				//to be able to view Swagger documentation comment the line below
				.anyRequest().authenticated()						   // if not specified, all other end points need a user login

				.and()
				.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ); // tell spring security NOT to create sessions
		
		// this request will go through many filters, make sure that the FIRST filter that is checked is
		// the filter for jwts, in order to make sure of that, the filter has to be checked before you check the 
		// username & password (filter)
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
				
		
		return http.build();
	} 
	
	// Encoder -> method that will be used to encode/decode user passwords
	@Bean
	protected PasswordEncoder encoder() {
		
		// plain text encoder -> won't do any actual encoding
		//return NoOpPasswordEncoder.getInstance();
		
		return new BCryptPasswordEncoder();
		
	}
	
	// load the encoder & user details service that are needed for spring security to do authentication
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());

		return authProvider;
	}
	
	// can autowire and access the authentication manager (manages authentication (login) of our project)
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	
}




