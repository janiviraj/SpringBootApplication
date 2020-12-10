package com.neu.flightbooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightbooking.model.Flight;
import com.neu.flightbooking.model.UserDetails;
import com.neu.flightbooking.view.SearchFlighDTO;

public interface SearchRepository extends CrudRepository<Flight, Long>  {
	
	@Query("SELECT f.flight_id, f.location, f.destination, f.departure_time, f.arrival_time, f.seats FROM Flight f"
			+ " WHERE f.location=:location AND f.destination=:destination")
	public List<Flight> getSearchResults(@Param("location") String location, 
			@Param("destination") String destination);
	
	@Query("SELECT u FROM Flight u WHERE u.location=?1 AND u.destination=?2 and u.seats>u.bookedSeats")
	List<Flight> findFlightByLocation(String location,String destination);
	
}
