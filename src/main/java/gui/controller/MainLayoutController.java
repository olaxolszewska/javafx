package gui.controller;

import java.io.IOException;
import java.util.List;

import data.dto.Airport;
import data.service.LufthansaDataRetriever;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainLayoutController {
	
	@FXML
	private Button cmdRetrieveData;
	
	@FXML
	private Circle point;
	
	@FXML
	private TextField IATAtextField;
	
	@FXML
	private ImageView img;
	
	@FXML
	private void retrieveData() {
		LufthansaDataRetriever dataRetriever = new LufthansaDataRetriever();
		try {
			dataRetriever.getAuthorizationToken();
			String text = IATAtextField.getText();
			List<Airport> airports = dataRetriever.retrieveAirportData(text, null, true);
			Airport airport = airports.get(0);
			point.setLayoutY(Conventer.convertLatitude(airport.getCoordinates().getLatitude()));
			point.setLayoutX(Conventer.convertLongitude(airport.getCoordinates().getLongitude()));
			point.setVisible(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		img.setImage(new Image("file:" + System.getProperty("user.dir") + "/assets/world_map.jpg"));
	}

}
