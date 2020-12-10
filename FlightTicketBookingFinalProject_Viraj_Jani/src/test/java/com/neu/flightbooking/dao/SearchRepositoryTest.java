package com.neu.flightbooking.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.neu.flightbooking.model.UserDetails;
import com.neu.flightbooking.service.CredentialServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = MyWebConfig.class)
class SearchRepositoryTest {
	@Autowired
    private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Mock
	private CredentialServiceImpl mockRepository;
	
	
//	@Before
//	public void init() {

//	Optional<UserDetails> details= mockRepository.findById(2l);
//	System.out.println("Details "+details);
//	}
	
	   @Before
	    public void setUp(){
	        MockitoAnnotations.initMocks(this);
//	        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
//	        this.mockMvc = builder.build();
	        
	    }
	     
	@Test
	void test() throws Exception  {
//		fail("Not yet implemented");
		UserDetails details= mockRepository.validatedlogin("viraj","viraj");
		System.out.println("Details "+details);
	}

}
