package com.neu.flightbooking.config;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	class MyAccessDeniedHelper implements AccessDeniedHandler {

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
				AccessDeniedException accessDeniedException) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			try {

				OutputStream os = response.getOutputStream();
				os.write("Access Denied. Unauthroized Access Detected.".getBytes());
				os.close();
			} catch (Exception e) {
				throw new ServletException();
			}

		}

	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/*").permitAll().and().formLogin().loginPage("/login/page")
				.defaultSuccessUrl("/login/redirectLogin").failureUrl("/login/page").usernameParameter("userid")
				.passwordParameter("password").and().logout().logoutSuccessUrl("/login/logout").and().

				exceptionHandling().accessDeniedHandler(new MyAccessDeniedHelper()).and().csrf().disable();

	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}