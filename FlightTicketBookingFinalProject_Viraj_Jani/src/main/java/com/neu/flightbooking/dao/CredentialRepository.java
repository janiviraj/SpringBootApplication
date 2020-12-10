package com.neu.flightbooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightbooking.model.Credential;
import com.neu.flightbooking.model.UserDetails;

public interface CredentialRepository extends CrudRepository<Credential, Long>{
	
	@Query("SELECT c FROM Credential c where c.username = :uname AND c.password=:pwd")
	public List<Credential> getCredential(@Param("uname") String username, 
			@Param("pwd") String password); 
}

