package com.neu.flightbooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neu.flightbooking.model.Credential;
import com.neu.flightbooking.model.UserDetails;

public interface UserDetailsRespository extends CrudRepository<UserDetails, Long>{

	@Query("SELECT c FROM UserDetails c where c.userid = :uname AND c.password=:password")
	public List<UserDetails> authenticateUser(@Param("uname") String username, 
			@Param("password") String password);
	
	@Query("SELECT c FROM UserDetails c where c.userid = :uname")
	public UserDetails findByUserName(@Param("uname") String username);
}

