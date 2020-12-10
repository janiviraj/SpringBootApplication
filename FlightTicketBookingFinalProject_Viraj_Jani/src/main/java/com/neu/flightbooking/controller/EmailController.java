package com.neu.flightbooking.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.neu.flightbooking.dao.FlightRespository;
import com.neu.flightbooking.model.BookFlight;
import com.neu.flightbooking.model.Flight;
import com.neu.flightbooking.model.UserDetails;


@Controller
public class EmailController {

	@Autowired
	FlightRespository flightRespository;

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	public ModelAndView sendMail(@ModelAttribute("bookFlight") BookFlight flightDetails)
			throws EmailException, MalformedURLException {
		// Create the attachment
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		UserDetails user = (UserDetails) session.getAttribute("userdetails");

		Optional<Flight> optional = flightRespository.findById(flightDetails.getFlight_id());
		File pdfFile = new File("ticket" + "Flight" + ".pdf");
		ModelAndView mav = new ModelAndView("bookFlight");
		if (optional.isPresent()) {
			Flight f = optional.get();
			// 1. Create document
			Document document = new Document(PageSize.A4, 40, 40, 40, 40);

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
				try {
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(pdfFile.getCanonicalPath());
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription("Your Flight Tickets");
					attachment.setName(user.getFirstname() + ".pdf");

					// Create the email message
					MultiPartEmail mtEmail = new MultiPartEmail();
					mtEmail.setHostName("smtp.gmail.com");
					mtEmail.setSmtpPort(587);
					mtEmail.setSSLOnConnect(true);
					mtEmail.setAuthenticator(new DefaultAuthenticator("janiviraj.2016@gmail.com", "Prozon@1234"));
					mtEmail.addTo(flightDetails.getPemail(), "John Doe");
					mtEmail.setFrom("janiviraj.2016@gmail.com", "Me");
					mtEmail.setSubject("Flight Tickets");
					mtEmail.setMsg("Hello," + "Kindly Find the tickets attached");

					// add the attachment
					mtEmail.attach(attachment);

					// send the email
					mtEmail.send();
					return mav;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					ModelAndView mav2 = new ModelAndView("bookFlight");
					return mav2;
					//e.printStackTrace();
				}
			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				ModelAndView mav1 = new ModelAndView("bookFlight");
				return mav1;
				//e.printStackTrace();
			}
		}
		
		return mav;

	}
}
