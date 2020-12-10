package com.neu.flightbooking.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.neu.flightbooking.dao.UserDetailsRespository;
import com.neu.flightbooking.model.UserDetails;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserDetailsRespository userDetailsRespository;
	

	// UserDetailsService overirided function

	@Transactional(readOnly = true)
	@Override
	public org.springframework.security.core.userdetails.User loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		System.out.println("Load user by username ==> "+username);
		UserDetails user = userDetailsRespository.findByUserName(username);
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		session.setAttribute("userdetails", user);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
		 org.springframework.security.core.userdetails.User user2=buildUserForAuthentication(user, authorities);
		 System.out.println("Load user by username ==> "+user2);
		return user2; 

	}

	// Converts com.flightbooking.assignment.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(UserDetails user,
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUserid(), user.getPassword(), true, true,
				true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities

		setAuths.add(new SimpleGrantedAuthority(userRoles));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}
