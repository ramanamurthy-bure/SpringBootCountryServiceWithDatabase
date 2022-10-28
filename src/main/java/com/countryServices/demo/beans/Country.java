package com.countryServices.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

	@Id
	@Column(name = "countryID")
	int countryID;

	@Column(name = "countryName")
	String countryName;

	@Column(name = "countryCapital")
	String countryCapital;

	public Country() {

	}

	public Country(int countryID, String countryName, String countryCapital) {
		this.countryID = countryID;
		this.countryName = countryName;
		this.countryCapital = countryCapital;
	}

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCapital() {
		return countryCapital;
	}

	public void setCountryCapital(String countryCapital) {
		this.countryCapital = countryCapital;
	}
}
