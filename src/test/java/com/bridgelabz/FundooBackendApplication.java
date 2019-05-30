package com.bridgelabz;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.user.controller.UserController;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = UserController.class)
//@WebMvcTest(value = FundooBackendApplication.class)
public class FundooBackendApplication {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private UserController userController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(new CorsFilter())
                .build();

	}
	
	@Test
	public void contextLoads() {}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	public void testRegister() throws Exception {
		System.out.println("Inside test Register");
		UserDTO userDto = new UserDTO("Tasif Mohammed","tasifmd96@gmail.com","tasifmd","9178184086");
		mockMvc.perform(MockMvcRequestBuilders
				.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId").exists());
				
	}
	
	
}
