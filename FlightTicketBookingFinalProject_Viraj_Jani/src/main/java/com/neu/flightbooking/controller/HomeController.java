
package com.neu.flightbooking.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.neu.flightbooking.model.UserDetails;

@Controller
public class HomeController {

	@GetMapping("/")
	public ModelAndView startup() {
		System.out.println("Test");
		ModelAndView mav = new ModelAndView("login");
		UserDetails userdetails = new UserDetails();
		mav.addObject("userdetails", userdetails);
		return mav;
	}
}
