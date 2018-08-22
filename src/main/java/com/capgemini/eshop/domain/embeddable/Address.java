package com.capgemini.eshop.domain.embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5550204893425710981L;

	@Column(nullable = false, length = 40)
	private String street;

	@Column(nullable = false, length = 40)
	private String city;

	@Column(name = "postal_code", nullable = false, length = 10)
	private String postalCode;

	@Column(name = "hause_number", nullable = false, length = 15)
	private String hauseNumber;

	@Column(nullable = false, length = 20)
	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getHauseNumber() {
		return hauseNumber;
	}

	public void setHauseNumber(String hauseNumber) {
		this.hauseNumber = hauseNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Address() {
	}

	public Address(String street, String city, String postalCode, String hauseNumber, String country) {
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.hauseNumber = hauseNumber;
		this.country = country;
	}

	public boolean checkAddress() {
		if (street.isEmpty() || city.isEmpty() || postalCode.isEmpty() || hauseNumber.isEmpty() || country.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((hauseNumber == null) ? 0 : hauseNumber.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (hauseNumber == null) {
			if (other.hauseNumber != null)
				return false;
		} else if (!hauseNumber.equals(other.hauseNumber))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

}
