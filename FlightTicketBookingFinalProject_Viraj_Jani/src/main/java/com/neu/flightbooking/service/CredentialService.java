package com.neu.flightbooking.service;

import com.neu.flightbooking.model.Credential;
import com.neu.flightbooking.model.UserDetails;

public interface CredentialService {
	Credential getCredential(String username, String password);
	public UserDetails setUserDetail(UserDetails userDetails);
	public UserDetails validatedlogin(String userid, String password);
}
