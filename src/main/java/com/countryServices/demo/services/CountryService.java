package com.countryServices.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryServices.demo.beans.Country;
import com.countryServices.demo.controllers.AddResponse;
import com.countryServices.demo.repositories.CountryRepository;

@Service
@Component
public class CountryService {

	@Autowired
	CountryRepository countryRep;

	public List<Country> getAllCountries() {
		// To get all the rows from the table
		List<Country> countries = countryRep.findAll();
		return countries;
	}

	public Country getCountryById(int cntryID) {
		List<Country> countries = countryRep.findAll();
		Country country = null;
		for (Country cnty : countries) {
			if (cnty.getCountryID() == cntryID)
				country = cnty;
		}
		return country;
	}

	public Country getCountryByName(String ctryName) {
		List<Country> countries = countryRep.findAll();
		Country country = null;
		for (Country cnty : countries) {
			if (cnty.getCountryName().equalsIgnoreCase(ctryName))
				country = cnty;
		}
		return country;
	}

	public Country addCountry(Country country) {
		country.setCountryID(getMaxID());
		countryRep.save(country);
		return country;
	}

	// utility method to get id
	public int getMaxID() {
		return countryRep.findAll().size() + 1;

	}

	public Country updateCountry(Country country) {
		countryRep.save(country);
		return country;
	}

	public AddResponse deleteCountry(int ctryID) {
		countryRep.deleteById(ctryID);
		AddResponse resmsg = new AddResponse();
		resmsg.setMessage("Country deleted!...");
		resmsg.setId(ctryID);
		return resmsg;
	}
}
