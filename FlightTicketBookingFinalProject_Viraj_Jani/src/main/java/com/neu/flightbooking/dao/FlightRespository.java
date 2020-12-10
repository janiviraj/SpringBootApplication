package com.neu.flightbooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightbooking.model.BookFlight;
import com.neu.flightbooking.model.Credential;
import com.neu.flightbooking.model.Flight;

public interface FlightRespository extends CrudRepository<Flight, Long> {
	

	
}
