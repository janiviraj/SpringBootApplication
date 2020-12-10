package com.neu.flightbooking.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.neu.flightbooking.dao.UserDetailsRespository;
import com.neu.flightbooking.model.UserDetails;
import com.neu.flightbooking.service.CredentialService;
import com.neu.flightbooking.view.SearchFlighDTO;

@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private CredentialService myUserDetailsService;
	@Autowired
	private UserDetailsRespository userDetailsRepository;

	@RequestMapping("/page")
	public ModelAndView login(Map<String, Object> model) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		logger.debug("Trying to login");
		if (session.getAttribute("userdetails") == null) {
			logger.debug("session attribute not available, sending to login page..");
			ModelAndView mav = new ModelAndView("login");
			UserDetails userdetails = new UserDetails();
			model.put("userdetails", userdetails);
			return mav;
		} else {
			logger.debug("session attribute	 available");
			ModelAndView mav = null;
			UserDetails ud = (UserDetails) session.getAttribute("userdetails");
			logger.debug("user details: " + ud);
			logger.debug("user details: " + ud.getFirstname());

			session.setAttribute("userdetails", ud);

			logger.debug("user role: " + ud.getRole());
			if ("admin".equalsIgnoreCase(ud.getRole())) {
				logger.debug("its admin");
				mav = new ModelAndView("admin");
				mav.addObject("userdetails", ud);
				return mav;
			} else if ("user".equalsIgnoreCase(ud.getRole())) {
				logger.debug("its user");
				mav = new ModelAndView("searchFlights");
				mav.addObject("searchFlight", new SearchFlighDTO());
				mav.addObject("userdetails", ud);
				return mav;
			}
			return new ModelAndView("failed-login");
		}
	}

//For Signup
	@RequestMapping("/signup")
	public String goToSignUpPage(Map<String, Object> model) {
		UserDetails userDetails = new UserDetails();
		model.put("userdetails", userDetails);
		return "signup";
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public ModelAndView signUp(@ModelAttribute("userdetails") UserDetails userDetails) {
		userDetails = myUserDetailsService.setUserDetail(userDetails);
		return new ModelAndView("login");
	}

	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String signOutPage(HttpServletRequest request, HttpServletResponse response) {

		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("authenticate " + authenticate);
		if (authenticate != null) {
			new SecurityContextLogoutHandler().logout(request, response, authenticate);
		}
		logger.debug("logged out, redirecting..");
		return "redirect:/login/page";
	}

	// CheckLogin for AJAX
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String checkLogin(@RequestParam String userid, @RequestParam String password) {
		UserDetails userdetails1 = myUserDetailsService.validatedlogin(userid, password);
		if (userdetails1 != null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("userdetails", userdetails1);
			return "{\"success\":\"true\"}";
		}
		return "{\"success\":\"false\"}";
	}

	@RequestMapping(value = "/checkUserExists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String checkUserExists(@RequestParam String userid) {
		UserDetails userdetails1 = userDetailsRepository.findByUserName(userid);
		if (userdetails1 != null) {
			return "{\"success\":\"true\"}";
		}
		return "{\"success\":\"false\"}";
	}

	@RequestMapping(value = "/redirectLogin", method = RequestMethod.GET)
	public ModelAndView redirectLogin(@ModelAttribute("userdetails") UserDetails userdetails) {
		logger.debug("In Redirect Login");
		logger.debug("Authority " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("userdetails") != null) {
			UserDetails userdetails1 = (UserDetails) session.getAttribute("userdetails");
			logger.debug("userdetails1: " + userdetails1.getId());

			ModelAndView mav = null;
			if ("admin".equalsIgnoreCase(userdetails1.getRole())) {
				mav = new ModelAndView("admin");
				mav.addObject("userdetails", userdetails1);
			} else if ("user".equalsIgnoreCase(userdetails1.getRole())) {

				session.setAttribute("userdetails", userdetails1);
				mav = new ModelAndView("searchFlights");
				mav.addObject("searchFlight", new SearchFlighDTO());
				mav.addObject("userdetails", userdetails1);

			}

			session.setAttribute("userdetails", userdetails1);
			return mav;
		} else {
			return new ModelAndView("failed-login");
		}
	}

}