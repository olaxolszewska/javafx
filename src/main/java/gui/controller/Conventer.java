package gui.controller;

public class Conventer {
	private static final int LON_MAX = 180;
	private static final int LAT_MAX = 85;

	public static double convertLatitude(double latitude) {
		double pixel;

		latitude = LAT_MAX + latitude;
		if (latitude <= 0) {
			latitude = Math.abs(latitude) + LAT_MAX;
		} else {
			latitude = Math.abs(latitude - LAT_MAX);
		}
		pixel = latitude * 2.94;
		return pixel;

	}

	public static double convertLongitude(double longitude) {
		double pixelW;

		longitude = LON_MAX - Math.abs(longitude);

		pixelW = longitude * 1.67;
		return pixelW;
	}

}