package com.bridgelabz.fundoo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class FundooBackendApplicationTest {
	
	@Autowired
	private MockMvc mvc;
	  
	@Autowired
	private WebApplicationContext wac;
	  
	@InjectMocks
	private UserDTO userDto;
	
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	  
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	
	
	@Test
	public void registerTest() throws Exception
	{
		userDto.setName("Tasif");
		userDto.setEmail("tasif@gmail.com");
		userDto.setPassword("12346788");
		userDto.setMobileNumber("1234567890");
		mvc.perform( MockMvcRequestBuilders
	      .post("/user/register")
	      .content(asJsonString(userDto))
	      //.content(asJsonString(new UserDTO("Perweez","perweez@gmail.com","1234567","8280946115")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	  	  .andDo(print())
	      .andExpect(status().isOk());
	}
	
	@Test
	public void loginTest() throws Exception {
		 mvc.perform( MockMvcRequestBuilders
				 .post("/user/login")
				 .content(asJsonString(new LoginDTO("tasifmd96@gmail.com", "tasifmd")))
				 .contentType(MediaType.APPLICATION_JSON)
			     .accept(MediaType.APPLICATION_JSON))
			  	 .andDo(print())
			     .andExpect(status().isOk());
	}
}
