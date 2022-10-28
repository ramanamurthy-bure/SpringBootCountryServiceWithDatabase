package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.countryServices.demo.beans.Country;
import com.countryServices.demo.repositories.CountryRepository;
import com.countryServices.demo.services.CountryService;

// Using Junit5 + Mockito

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { CountryServiceMockitoTests.class })
public class CountryServiceMockitoTests {

	@Mock
	CountryRepository countryRep;

	@InjectMocks
	CountryService countryService;

	public List<Country> myTestCountries;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		// countryService.getAllCountries();
		/*
		 * This actual method will call the database. Using the below code we can mockit
		 * database and avoid database dependency. We can provide our data for testing.
		 */

		myTestCountries = new ArrayList<Country>();
		myTestCountries.add(new Country(1, "India", "Delhi"));
		myTestCountries.add(new Country(2, "Washington", "USA"));
		// The below Mocking statement will return our own mock data instead of
		// executing the method countryRep.findAll()
		when(countryRep.findAll()).thenReturn(myTestCountries);
		List<Country> resultCountries = countryService.getAllCountries(); //
		assertEquals(2, resultCountries.size());

	}

	@Test
	@Order(2)
	public void test_getCountryByID() {
		myTestCountries = new ArrayList<Country>();
		myTestCountries.add(new Country(1, "India", "Delhi"));
		myTestCountries.add(new Country(2, "Washington", "USA"));
		int countryId = 1;

		when(countryRep.findAll()).thenReturn(myTestCountries); // Mocking statement(countryRep.findAll()) to eliminate
																// the database
																// dependency
		assertEquals(1, countryService.getCountryById(countryId).getCountryID());

	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		myTestCountries = new ArrayList<Country>();
		myTestCountries.add(new Country(1, "India", "Delhi"));
		myTestCountries.add(new Country(2, "Washington", "USA"));
		String countryName = "Washington";

		when(countryRep.findAll()).thenReturn(myTestCountries); // Mocking statement(countryRep.findAll()) to eliminate
																// the database
																// dependency
		assertEquals("Washington", countryService.getCountryByName(countryName).getCountryName());

	}

	@Test
	@Order(4)
	public void test_addCountry() {
		Country country = new Country(3, "Germany", "Berlin");

		when(countryRep.save(country)).thenReturn(country); // Mocking statement(countryRep.save(country))
		Country returnedCntry = countryService.addCountry(country);

		assertEquals(country, returnedCntry);

	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		Country country = new Country(2, "Japan", "Tokyo");

		when(countryRep.save(country)).thenReturn(country); // Mocking statement(countryRep.save(country))
		Country updatedCntry = countryService.updateCountry(country);

		assertEquals(country, updatedCntry);

	}

	@Test
	@Order(6)
	public void test_deleteCountry() {
		int countryId = 1;
		countryService.deleteCountry(countryId);
		verify(countryRep, times(1)).deleteById(countryId); // Mocking statement
	}
}
