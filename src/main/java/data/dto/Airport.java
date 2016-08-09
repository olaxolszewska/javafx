package data.dto;

import data.Coordinates;

public class Airport {

	private String airportCode;
	private String cityCode;
	private String countryCode;
	private Coordinates coordinates;
	private String name;

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "IATA code: " + airportCode + ", country code: " + countryCode + ", city code: " + cityCode;
	}

	public void setName(String name) {
		this.name = name;
	}

}
