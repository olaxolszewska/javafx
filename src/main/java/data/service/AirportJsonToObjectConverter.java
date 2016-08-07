package data.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import data.Coordinates;
import data.dto.Airport;
/**
 * 
 * @author PKOWALEW
 *
 */
public class AirportJsonToObjectConverter {
	
	public List<Airport>convertSingleRequest(JsonNode json, boolean isSpecificAirport) {
		List<Airport> airports = new ArrayList<>();
		JsonNode airportNodes = json.get("AirportResource").get("Airports").get("Airport");
		if (isSpecificAirport) {
			airportNodes = json.get("AirportResource").get("Airports");
		}
		for (JsonNode objectNode : airportNodes) {
			Airport airport = new Airport();
			airport.setAirportCode(objectNode.get("AirportCode").asText());
			airport.setCityCode(objectNode.get("CityCode").asText());
			airport.setCountryCode(objectNode.get("CountryCode").asText());
			airport.setName(objectNode.get("Names").get("Name").get(0).get("$").asText());
			
			JsonNode coordinatesNode = objectNode.get("Position").get("Coordinate");
			Coordinates coordinates = new Coordinates();
			coordinates.setLatitude(coordinatesNode.get("Latitude").asDouble());
			coordinates.setLongitude(coordinatesNode.get("Longitude").asDouble());
			
			airport.setCoordinates(coordinates);
			
			airports.add(airport);
		}

		return airports;
	}

}
