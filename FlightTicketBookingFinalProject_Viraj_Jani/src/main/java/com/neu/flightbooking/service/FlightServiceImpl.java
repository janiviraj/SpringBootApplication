package com.neu.flightbooking.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.neu.flightbooking.dao.BookFlightRepository;
import com.neu.flightbooking.dao.FlightRespository;
import com.neu.flightbooking.model.BookFlight;
import com.neu.flightbooking.model.Flight;
import com.neu.flightbooking.model.UserDetails;



@Service
public class FlightServiceImpl {

	@Autowired
	FlightRespository flightRespository;
	@Autowired
	BookFlightRepository bookFlightRepository;

	public Flight saveflight(Flight flight) {
		return flightRespository.save(flight);
	}

	public List<Flight> viewFlight() {
		return null;
	}

	public List<Flight> updateFlights() {
		return null;
	}

	public BookFlight insertBookDetails(BookFlight bookFlight, UserDetails ud) {
//		userDetails.setRole("user");
		bookFlight.setUserDetails(ud);
		return bookFlightRepository.save(bookFlight);
	}

	public boolean updateBookedSeats(long flightId) {
//		userDetails.setRole("user");
		Optional<Flight> optional = flightRespository.findById(flightId);
		if (optional.isPresent()) {
			Flight f = optional.get();
			f.setBookedSeats(f.getBookedSeats() + 1);
			flightRespository.save(f);
			return true;
		
		}

		return false;
	}
	
	
	public void cancleBookDetails(Long bookingId, UserDetails ud) {
		//userDetails.setRole("user");
		//bookingId.setUserDetails(ud);
		bookFlightRepository.deleteById(bookingId);
	}

	public File generatePDF(BookFlight flightDetails, UserDetails user,boolean sendEmail) {
		Optional<Flight> optional = flightRespository.findById(flightDetails.getFlight_id());
		File pdfFile = new File("ticket" + user.getFirstname() + ".pdf");
		if (optional.isPresent()) {
			Flight f = optional.get();
			// 1. Create document
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			
			// 2. Create PdfWriter
			try {
				PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

				// 3. Open document
				document.open();

				// create data for ticket
				String ticketData = "Booking Id: 	" + flightDetails.getBid() + "\n\nFlight Ticket\n\n"
						+ "Name: 			" + flightDetails.getPname() + "\n" + "From: 			"
						+ flightDetails.getLocation() + "\n" + "To: 			" + flightDetails.getDestination()
						+ "\n" + "Departure Time: " + flightDetails.getDeparture_time() + "\n" + "Email: 		"
						+ flightDetails.getPemail() + "\n" + "Flight Details  \nDeparture Time - "
						+ f.getDeparture_time() + "\n Arrival Time - " + f.getArrival_time() + "\n Ticket Price - "
						+ f.getTicketCost();

				// 4. Add content
				document.add(new Paragraph(ticketData));

				// 5. Close document
				document.close();

			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pdfFile;
	}

}
