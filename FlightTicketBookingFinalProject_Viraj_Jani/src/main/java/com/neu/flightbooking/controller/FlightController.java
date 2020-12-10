package com.neu.flightbooking.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.neu.flightbooking.dao.BookFlightRepository;
import com.neu.flightbooking.dao.FlightRespository;
import com.neu.flightbooking.dao.SearchRepository;
import com.neu.flightbooking.model.BookFlight;
//import com.neu.flightbooking.model.FileUpload;
import com.neu.flightbooking.model.Flight;
import com.neu.flightbooking.model.UserDetails;
import com.neu.flightbooking.service.FlightServiceImpl;
import com.neu.flightbooking.util.DateHelper;
import com.neu.flightbooking.view.SearchFlighDTO;
import com.neu.flightbooking.controller.EmailController;

@Controller
public class FlightController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private FlightRespository flightRespository;
	@Autowired
	private BookFlightRepository bookFlightRepository;
	@Autowired
	private SearchRepository searchRespository;

	@Autowired
	private FlightServiceImpl flightServiceImpl;

	@Autowired
	private EmailController emailController;

	// Update Flight GET Method
	@PreAuthorize("isAuthenticated() and hasAuthority('admin')")
	@RequestMapping(value = "/updateFlights", method = RequestMethod.GET)
	public ModelAndView getAllFlights() {
		ModelAndView mav = new ModelAndView("updateFlights");
		List<Flight> flightList = (List) flightRespository.findAll();
		logger.debug("flightList " + flightList + " flightList " + flightList.size());

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);

			if (!flightList.isEmpty()) {
				mav.addObject("flightList", flightRespository.findAll());
				return mav;
			} else {
				ModelAndView mav1 = new ModelAndView("admin");
				return mav1;
			}
		} else {
			return new ModelAndView("falied-login");
		}
	}

	// Search Flight POST
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/searchFlights", method = RequestMethod.POST)
	public ModelAndView searchFlights(@Valid @ModelAttribute("searchFlight") SearchFlighDTO searchFlight,
			BindingResult result) {
		ModelAndView mav = new ModelAndView("searchResults");

		if (result.hasErrors()) {
			return new ModelAndView("searchFlights");
		}
		mav.addObject("searchFlight", searchFlight);
		List<Flight> flightList = searchRespository.findFlightByLocation(searchFlight.getLocation(),
				searchFlight.getDestination());
		flightList.forEach(flight -> {
			flight.setArrival_time(flight.getArrival_time().substring(11));
			flight.setDeparture_time(flight.getDeparture_time().substring(11));
		});
		logger.debug("in searchFlights*************");

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);
			if (flightList.isEmpty()) {
				result.rejectValue("location", "location", "No such flight found kindly search again");
				return new ModelAndView("searchFlights");

			} else {
				logger.debug("returnung null");
				// mav.addObject("flightList", flightList);
				// return mav;
				// ModelAndView mav = new ModelAndView("searchResults");
				mav.addObject("flightList", flightList);
				return mav;
			}

		} else {
			return new ModelAndView("failed-login");
		}
	}

	// Check Booked Flight
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/checkBookedHistory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String checkBookedHistory(@RequestParam String pname, @RequestParam String departure_time,
			@RequestParam String pphone, @RequestParam String flight_id) {
		departure_time = departure_time.substring(0, 11);
		System.out.println("Pname " + pname + " departure_time " + departure_time + " pphone  " + pphone);
		BookFlight bookFlight = bookFlightRepository.getMyBookingHistory(pname, departure_time, pphone,
				Long.parseLong(flight_id));
		logger.debug(" Travel bookFlight " + bookFlight);
		if (bookFlight != null) {
			return "{\"success\":\"true\"}";
		}
		return "{\"success\":\"false\"}";
	}

	// Add Flights Method
	@PreAuthorize("isAuthenticated() and hasAuthority('admin')")
	@RequestMapping(value = "/addFlight", method = RequestMethod.GET)
	public ModelAndView goToFlight(Map<String, Object> model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);

			ModelAndView mav = new ModelAndView("addflight");
			Flight flight = new Flight();
			mav.addObject("flight", flight);
			return mav;
		} else {
			return new ModelAndView("failed-login");
		}
	}

	// addflight POST Method
	@PreAuthorize("isAuthenticated() and hasRole('admin')")
	@RequestMapping(value = "/addflight", method = RequestMethod.POST)
	public ModelAndView addFlight(@Valid @ModelAttribute("flight") Flight flight, BindingResult result) {
		logger.debug("starting inserting...");
		ModelAndView mav = new ModelAndView("message");
		if (result.hasErrors()) {
			return new ModelAndView("addflight");
		}
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);
			flight.setDeparture_time(DateHelper.convertDate(flight.getDeparture_time()));
			flight.setArrival_time(DateHelper.convertDate(flight.getArrival_time()));

			logger.debug("final datetime: " + flight.getArrival_time() + ", " + flight.getDeparture_time());

			if (flight != null) {

				flightServiceImpl.saveflight(flight);
				mav = new ModelAndView("admin");
				mav.addObject("message", "Process done Successfully");
				return mav;
			}
		} else {

			return new ModelAndView("failed-login");
		}

		mav.addObject("message", "Error Occured");
		return mav;
	}

	// Book Flight Method
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/bookFlight")
	public ModelAndView goToFlight(@ModelAttribute("flight") Flight flight,
			@ModelAttribute("departure_date") String departure_date) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		logger.debug("departure_date " + departure_date);
		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);

			logger.debug("got this data: " + flight.getLocation());
			ModelAndView mav = new ModelAndView("bookFlight");
			BookFlight bookflight = new BookFlight();
			bookflight.setPname(ud.getFirstname() + " " + ud.getLastname());
			bookflight.setDeparture_time(departure_date + " " + flight.getDeparture_time());
			UserDetails userDetail = new UserDetails();
			userDetail.setId(ud.getId());
			bookflight.setUserDetails(userDetail);
			mav.addObject("flight", flight);
			mav.addObject("bookFlight", bookflight);
			return mav;
		} else {
			return new ModelAndView("failed-login");
		}

	}

	@RequestMapping(value = "/searchFlightPage")
	public ModelAndView goToFlight() {
		ModelAndView mav = new ModelAndView("searchFlights");
		mav.addObject("searchFlight", new SearchFlighDTO());

		return mav;
	}

	// Payment Page
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/paymentPage")
	public ModelAndView paymentPage() {
		ModelAndView mav = new ModelAndView("payment");

		return mav;
	}

	// final book method for pdf and email
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/finalBook", method = RequestMethod.POST)
	public ModelAndView insertBookFlight(@ModelAttribute("bookFlight") BookFlight bookFlight)
			throws MalformedURLException, EmailException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);

			// bookFlight.setId(ud.getId());
			bookFlight = flightServiceImpl.insertBookDetails(bookFlight, ud);
			flightServiceImpl.updateBookedSeats(bookFlight.getFlight_id());
			// flightServiceImpl.generatePDF(bookFlight, ud, true);
			emailController.sendMail(bookFlight);
			ModelAndView mav = new ModelAndView("searchFlights");
			mav.addObject("searchFlight", new SearchFlighDTO());
			return mav;

		} else {
			return new ModelAndView("failed-login");
		}
	}

	//Delete Ticket
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/cancleETicket", method = RequestMethod.GET)
	public ModelAndView cancleBookFlight(@ModelAttribute("bookingId") String bookingId)
			throws MalformedURLException, EmailException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);
			flightServiceImpl.cancleBookDetails(Long.parseLong(bookingId), ud);
			ModelAndView mav = new ModelAndView("searchFlights");
			mav.addObject("searchFlight", new SearchFlighDTO());
			return mav;

		} else {
			return new ModelAndView("failed-login");
		}
	}
	
	
	
	
	
	// To download Tickets
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/downloadETicket", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> downloadETicket(@ModelAttribute("bookingId") String bookingId) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			Optional<BookFlight> optional = bookFlightRepository.findById(Long.parseLong(bookingId));
			BookFlight booked = optional.get();
			try {

				emailController.sendMail(booked);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EmailException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			boolean sendEmail = false;

			File file = flightServiceImpl.generatePDF(booked, ud, sendEmail);
			if (!sendEmail) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				// Here you have to set the actual filename of your pdf
				String filename = "Ticket.pdf";
				headers.add("Content-Disposition", "inline; filename=" + filename);
				headers.setContentDispositionFormData(filename, filename);
				headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				ResponseEntity<byte[]> response = null;
				try {
					response = new ResponseEntity<>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers,
							HttpStatus.OK);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return response;
			}
		}
		return null;
	}

	public boolean checkDate(String date) {
		SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		try {
			Date date6 = formatter6.parse(date);
			logger.debug("date => " + date6);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Update Flights POST
	@PreAuthorize("isAuthenticated() and hasRole('admin')")
	@RequestMapping(value = "/updateFlights", method = RequestMethod.POST)
	public ModelAndView saveUpdatedFlights(@Valid @ModelAttribute("flight") Flight flight, BindingResult result) {
		ModelAndView mav = new ModelAndView("updateFlights");
		if (result.hasErrors()) {
			return mav;
		}
		if (flight.getDestination().equalsIgnoreCase(flight.getLocation())) {
			ObjectError error = new ObjectError("destination", "Source and destination can not be same.");
			result.addError(error);
			logger.debug("Source and destination can not be same.");
			result.rejectValue("destination", "error.destination", "Source and destination can not be same.");
			mav.addObject("flightList", flightRespository.findAll());
			mav.addObject("message", "Source and destination can not be same.");
			return mav;
		}
		if (!(checkDate(flight.getArrival_time()) && checkDate(flight.getDeparture_time()))) {
			logger.debug("Source and destination can not be same.");
			result.rejectValue("destination", "error.destination",
					"Arrival and departure date format error. Please correct.");
			mav.addObject("flightList", flightRespository.findAll());
			mav.addObject("message", "Arrival and departure date format error. Please correct.");
			return mav;
		}

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());

			flight = flightServiceImpl.saveflight(flight);
			logger.debug("updated to this: " + flight.getFlight_id() + ", " + flight.getLocation() + ", "
					+ flight.getDestination() + ", " + flight.getArrival_time() + ", " + flight.getDeparture_time()
					+ ", " + flight.getSeats());
			mav = new ModelAndView("admin");
			mav.addObject("searchFlight", new SearchFlighDTO());
			return mav;

		} else {
			return new ModelAndView("failed-login");
		}
	}

	// View Passengers
	@PreAuthorize("isAuthenticated() and hasRole('admin')")
	@RequestMapping(value = "/viewPassengers", method = RequestMethod.GET)
	public ModelAndView getAllPassengers() {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {

			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails: " + ud.getId());
			session.setAttribute("userdetails", ud);

			ModelAndView mav = new ModelAndView("viewPassengers");

			List<BookFlight> passList = (List<BookFlight>) bookFlightRepository.findAll();
			if (ud.getRole().trim().equalsIgnoreCase("admin")) {
				passList = (List<BookFlight>) bookFlightRepository.findAll();
			} else {
				// passList = (List) bookFlightRepository.getMyBookings(ud.getId());
				passList = (List<BookFlight>) bookFlightRepository.getMyBookings(ud.getId());
			}
			if (passList != null) {
				mav.addObject("flightList", passList);
				logger.debug("returning some list  321312");
				return mav;
			} else {
				return null;
			}
		} else {
			return new ModelAndView("failed-login");
		}
	}

}