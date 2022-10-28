package com.countryServices.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countryServices.demo.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

}
