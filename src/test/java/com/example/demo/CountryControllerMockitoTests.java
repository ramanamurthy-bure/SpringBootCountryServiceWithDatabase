package com.example.demo;

import static org.junit.Assert.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryServices.demo.beans.Country;
import com.countryServices.demo.controllers.CountryController;
import com.countryServices.demo.services.CountryService;

// Using Junit5 + Mockito

@TestMethodOrder(OrderAnnotation.class) // To run the tests in order we require this annotation
//To make the below class as test class we need to add this annotation @SpringBootTest
@SpringBootTest(classes = { CountryControllerMockitoTests.class })
public class CountryControllerMockitoTests {

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	public List<Country> myTestCountries;
	public Country country;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		// Our own test data for testing
		myTestCountries = new ArrayList<Country>();
		myTestCountries.add(new Country(1, "India", "Delhi"));
		myTestCountries.add(new Country(2, "USA", "Washington"));

		// The below statement is mocking - This will mock the
		// countryService.getAllCountries() method.
		// Which means whenever this method is invoked it will return myTestCountries as
		// a result
		// instead of talking to the CountryService class by which we are eliminating
		// the external dependency.
		// For CountryController class the external dependency is 'CountryService' class
		when(countryService.getAllCountries()).thenReturn(myTestCountries);
		ResponseEntity<List<Country>> returnCountries = countryController.getCountries();
		assertEquals(HttpStatus.FOUND, returnCountries.getStatusCode());
		assertEquals(2, returnCountries.getBody().size());

	}

	@Test
	@Order(2)
	public void test_getCountryByID() {
		Country country = new Country(2, "USA", "Washington");
		int cntryID = 2;
		when(countryService.getCountryById(cntryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryByID(cntryID);
		assertEquals(cntryID, res.getBody().getCountryID());
		assertEquals(HttpStatus.FOUND, res.getStatusCode());

	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		Country country = new Country(3, "UK", "London");
		String cntryName = "UK";
		when(countryService.getCountryByName(cntryName)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryByName(cntryName);
		assertEquals(cntryName, res.getBody().getCountryName());
		assertEquals(HttpStatus.FOUND, res.getStatusCode());

	}

	@Test
	@Order(4)
	public void test_addCountry() {
		Country country = new Country(4, "Australia", "Sydney");
		when(countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.addCountry(country);
		assertEquals(4, res.getBody().getCountryID());
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		Country newCntryToUpdate = new Country(3, "Japan", "Tokyo");
		int cntryID = 3;
		when(countryService.getCountryById(cntryID)).thenReturn(newCntryToUpdate);
		when(countryService.updateCountry(newCntryToUpdate)).thenReturn(newCntryToUpdate);

		ResponseEntity<Country> res = countryController.updateCountry(cntryID, newCntryToUpdate);
		assertEquals(3, res.getBody().getCountryID());
		assertEquals(HttpStatus.OK, res.getStatusCode());

	}

	@Test
	@Order(6)
	public void test_deleteCountry() {
		Country countryToDelete = new Country(4, "Australia", "Sydney");
		int cntryID = 4;
		when(countryService.getCountryById(cntryID)).thenReturn(countryToDelete);
		when(countryService.updateCountry(countryToDelete)).thenReturn(countryToDelete);

		ResponseEntity<Country> res = countryController.deleteCountry(cntryID);
		assertEquals(4, res.getBody().getCountryID());
		assertEquals(HttpStatus.OK, res.getStatusCode());

	}

}
