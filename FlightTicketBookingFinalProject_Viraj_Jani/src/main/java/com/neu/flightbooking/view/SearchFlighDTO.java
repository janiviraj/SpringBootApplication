package com.neu.flightbooking.view;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchFlighDTO {
	
	
	private Long search_id;
	@NotEmpty(message="Location can't be empty")
	private String location;
	@NotEmpty(message="destination can't be empty")
	private String destination;
	@NotEmpty(message="date can't be empty")
	private String departure_time;
	@NotEmpty(message="Number of seats cannot be zero")
	private String passenger;

	public Long getSearch_id() {
		return search_id;
	}

	public void setSearch_id(Long search_id) {
		this.search_id = search_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getPassenger() {
		return passenger;
	}

	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}

}
