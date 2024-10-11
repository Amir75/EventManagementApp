package com.event.mgmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.event.mgmt.config.JwtUtil;
import com.event.mgmt.dto.AuthRequest;
import com.event.mgmt.dto.AuthResponse;
import com.event.mgmt.dto.UserDto;
import com.event.mgmt.services.CustomUserDetailsService;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
		userDetailsService.registerNewUser(userDto);
		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
		final UserDetails userDetails = userDetailsService.loadUserByUsernameAndPassword(authRequest.getUsername(),authRequest.getPassword());
		if (userDetails != null) {
			System.out.println("userDetails" + userDetails);
			final String jwt = jwtUtil.generateToken(userDetails);
			return ResponseEntity.ok(new AuthResponse(jwt));
		}else {
			throw new Exception("Incorrect username or password");
		}
	}
}
