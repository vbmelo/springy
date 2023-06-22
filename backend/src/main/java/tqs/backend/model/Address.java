package tqs.backend.model;

public class Address {
	private String street;
	private String city;
	private String postalCode;
	private String country;

	// Patrameterized Constructor
	public Address(String street, String city, String postalCode, String country) {
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}

	// Default Constructor
	public Address() {
	}

	// Getters and Setters
	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.street = city;
	}

	public void setPostalCode(String postalCode) {
		this.street = postalCode;
	}

	public void setCountry(String country) {
		this.street = country;
	}

	// toString
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", postalCode=" + postalCode + ", country=" + country
				+ "]";
	}
}
