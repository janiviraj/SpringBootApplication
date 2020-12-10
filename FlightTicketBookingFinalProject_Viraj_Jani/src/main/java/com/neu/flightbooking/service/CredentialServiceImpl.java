package com.neu.flightbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.flightbooking.dao.CredentialRepository;
import com.neu.flightbooking.dao.UserDetailsRespository;
import com.neu.flightbooking.model.Credential;
import com.neu.flightbooking.model.UserDetails;
@Service
public class CredentialServiceImpl implements CredentialService{

	@Autowired
	CredentialRepository credentialDao;
	
	@Autowired
	UserDetailsRespository userDetailsRespository;
	@Override
	public Credential getCredential(String username, String password) {
		List<Credential> credential = credentialDao.getCredential(username, password);
		if (credential != null && credential.size() > 0) {
			System.out.println("***************" +  credential.get(0).getId());
			return credential.get(0);		
		} 
		return null;
	}

	public UserDetails setUserDetail(UserDetails userDetails) {
		userDetails.setRole("user");
		return userDetailsRespository.save(userDetails);
	}

	public UserDetails validatedlogin(String userid, String password) {
		List<UserDetails> userdetails = userDetailsRespository.authenticateUser(userid, password);
		System.out.println("userdetails list: " + userdetails);
		if (userdetails != null && userdetails.size() > 0) {
			System.out.println("***************" + userdetails.get(0).getId());
			return userdetails.get(0);
		}
		return null;
	}

}
