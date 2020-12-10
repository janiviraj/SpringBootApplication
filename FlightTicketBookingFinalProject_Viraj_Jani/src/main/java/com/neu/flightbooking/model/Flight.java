package com.neu.flightbooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NegativeOrZero;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.NotNull;;

@Entity
@Table(name = "flight")

public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long flight_id;
	@NotEmpty(message = "Location can't be empty")
	private String location;
	@NotEmpty(message = "destination can't be empty")
	private String destination;

	@Column(name = "departure_time")
	@NotBlank(message = "bookedSeats can't be empty")
	private String departure_time;
	@NotBlank(message = "bookedSeats can't be empty")
	private String arrival_time;

	@NotNull
	private int seats;
	@Digits(integer = 99999, fraction = 0)
	@NotNull
	private int bookedSeats = 0;
	@Digits(integer = 99999, fraction = 0)
	@NotNull

	@Min(value = 200, message = "ticket cost can't be less than 20")
	private int ticketCost = 0;

	public String getDeparture_time() {
		return departure_time;
	}

	public int getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public Long getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(Long flight_id) {
		this.flight_id = flight_id;
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

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(int ticketCost) {
		this.ticketCost = ticketCost;
	}
}
