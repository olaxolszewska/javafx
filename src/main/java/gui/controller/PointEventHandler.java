package gui.controller;

import data.dto.Airport;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class PointEventHandler implements EventHandler<MouseEvent> {

	private Airport airport;

	public PointEventHandler(Airport airport) {
		this.airport = airport;
	}

	@Override
	public void handle(MouseEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(airport.getName());
		alert.setHeaderText(airport.getAirportCode());
		alert.setContentText(airport.toString());
		alert.show();

	}
}
