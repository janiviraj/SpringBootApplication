package com.neu.flightbooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightbooking.model.BookFlight;
import com.neu.flightbooking.model.Flight;
import com.neu.flightbooking.model.UserDetails;

public interface BookFlightRepository extends CrudRepository<BookFlight, Long> {

	@Query("SELECT c FROM BookFlight c where c.userDetails.id  =:userId")
	public List<BookFlight> getMyBookings(@Param("userId") long userId);
	
	//@Query("select b from flight_booking.bookflight as b inner join flight_booking.userdetails as u on u.id = b.id")
	//public List<BookFlight> getMyBookings(@Param("userId") long userId);


	@Query("SELECT c FROM BookFlight c where c.pname like :pname and c.departure_time like %:departure_time% and c.pphone like %:pphone% and c.flight_id = :flight_id")
	public BookFlight getMyBookingHistory(@Param("pname") String pname, @Param("departure_time") String departure_time,
			@Param("pphone") String pphone, @Param("flight_id") long flight_id);
}
