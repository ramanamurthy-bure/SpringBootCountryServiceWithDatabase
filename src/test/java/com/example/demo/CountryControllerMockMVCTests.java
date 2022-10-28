package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryServices.demo.beans.Country;
import com.countryServices.demo.controllers.CountryController;
import com.countryServices.demo.services.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Using Junit5 + MOckMVC + Mockito
@ComponentScan(basePackages = "com.countryServices.demo") // To specify the base package
@AutoConfigureMockMvc
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class) // To run the tests in order we require this annotation
//To make the below class as test class we need to add this annotation @SpringBootTest
@SpringBootTest(classes = { CountryControllerMockMVCTests.class })
public class CountryControllerMockMVCTests {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	public List<Country> myTestCountries;
	public Country country;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test
	@Order(1)
	public void test_getAllCountries() {
		myTestCountries = new ArrayList<Country>();
		myTestCountries.add(new Country(1, "India", "Delhi"));
		myTestCountries.add(new Country(2, "USA", "Washington"));

		when(countryService.getAllCountries()).thenReturn(myTestCountries);
		try {
			this.mockMvc.perform(get("/getcountries")).andExpect(status().isFound()).andDo(print());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(2)
	public void test_getCountryByID() {
		country = new Country(2, "USA", "Washington");
		int cntryID = 2;
		when(countryService.getCountryById(cntryID)).thenReturn(country);
		try {
			this.mockMvc.perform(get("/getcountries/{id}", cntryID)).andExpect(status().isFound())
					.andExpect(MockMvcResultMatchers.jsonPath(".countryID").value(2))
					.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
					.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington")).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		country = new Country(2, "USA", "Washington");
		String cntryName = "USA";
		when(countryService.getCountryByName(cntryName)).thenReturn(country);
		try {
			this.mockMvc.perform(get("/getcountries/countryname").param("name", cntryName))
					.andExpect(status().isFound()).andExpect(MockMvcResultMatchers.jsonPath(".countryID").value(2))
					.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
					.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington")).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(4)
	public void test_addCountry() {
		country = new Country(3, "Germany", "Berline");

		when(countryService.addCountry(country)).thenReturn(country);

		try {

			ObjectMapper mapper = new ObjectMapper();
			String strJsonBody = mapper.writeValueAsString(country);

/*			this.mockMvc.perform(post("/addcountry").content(strJsonBody).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status.isc)					
					);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
