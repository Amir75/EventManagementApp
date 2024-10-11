package com.event.mgmt.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.event.mgmt.dto.UserDto;
import com.event.mgmt.entity.Role;
import com.event.mgmt.entity.User;
import com.event.mgmt.repository.RoleRepository;
import com.event.mgmt.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	

	@Override
	@Cacheable(value = "users")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toSet()));
	}
	
	
	public UserDetails loadUserByUsernameAndPassword(String username,String password) throws Exception {
		User user = userRepository.findByUsernameAndPassword(username,password)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toSet()));
	}

	public User registerNewUser(UserDto userDto) {
		if (userRepository.findByUsername(userDto.getUsername()).isPresent())

		{
			throw new RuntimeException("User already exists");
		}

		User user = new User();
		user.setUsername(userDto.getUsername());

		user.setPassword(userDto.getPassword());
		Set<Role> roles = new HashSet<>();
		for (String roleName : userDto.getRoles()) {
			System.out.println("roleName::::"+roleName);
			Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
			roles.add(role);
		}
		user.setRoles(roles);
		return userRepository.save(user);
	}

}
