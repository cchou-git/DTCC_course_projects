package edu.edu.csc241.aircraft;
 
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;


public class Airplane implements Observer {
	public static Double RadiansPerDegree = Math.PI / 180.0;
	public static Double halfPI = Math.PI / 2.0;
	 
	Double direction = 30.0;     // 0 - 180  
	Double lastSystemTime;  
	
	public Airplane(Double aTime) {
		lastSystemTime = aTime;
	} 

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Airplane updating!" + toString());
		if (lastTime == null) {
			lastTime = (Double) arg;
		} else {
			Double currentTime = (Double) arg;
			Double elapsedSeconds = (currentTime - lastTime) / 1000.0;
			move(elapsedSeconds);
			lastTime = currentTime;
		}
	} 
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" Spped (miles Per Hour) = " + milesPerHour);
		sb.append(" X = " + this.currentLongitude);
		sb.append(" Y = " + this.currentLatitude);
		sb.append(" Heading = " + headingInRadian + "(" + (headingInRadian / Math.PI) * 180.0 + ")");
		return sb.toString();
	}
	
	public String getData() {
		StringBuffer sb = new StringBuffer();
		sb.append("" + milesPerHour + "|" + this.currentLatitude + "|" + this.currentLongitude + "|" + headingInRadian + "|" + (headingInRadian / Math.PI) * 180.0);
		return sb.toString();
	}
	
	Double lastTime = null;
	
	 
	Double milesPerHour;
	Double userHeading;
	final Double milesPerLattitudeDegree = 69.0;
	Double milesPerLongitudeDegree = 69.172;
	private final Double SecondsInAnHour = 3600.0;
	
	Double lastLongitude = -74.0059;
	Double lastLatitude = 40.7127;
	Double currentLongitude = -74.0059;
	Double currentLatitude = 40.7127; 
	Double headingInRadian = 0.0; 
	
	void move(Double numberOfSeconds) { 
		Double milesPerSecond = milesPerHour / SecondsInAnHour; 
		Double distance = milesPerSecond * numberOfSeconds;  
		 
		Double deltaX = (Math.cos(headingInRadian) * distance);
		Double deltaY = (Math.sin(headingInRadian) * distance); 
		 
		lastLongitude = currentLongitude;
		lastLatitude = currentLatitude;
		currentLongitude = (deltaX / milesPerLongitudeDegree) + lastLongitude;
		currentLatitude = (deltaY / milesPerLattitudeDegree) + lastLatitude; 
	}

	public Double getLastSystemTime() {
		return lastSystemTime;
	}

	public void setLastSystemTime(Double lastSystemTime) {
		this.lastSystemTime = lastSystemTime;
	}

	public Double getMilesPerHour() {
		return milesPerHour;
	}

	public void setMilesPerHour(Double milesPerHour) {
		this.milesPerHour = milesPerHour;
	} 

	public Double getHeadingInRadian() {
		return headingInRadian;
	}

	public void setHeadingInRadian(Double headingInRadian) {
		this.headingInRadian = headingInRadian;
	}	
	
	public void setUserHeading(Double displayHeadingInDegree) {
		userHeading = displayHeadingInDegree;
		convertFromUserHeadingToInternalHeadingInRadian();
	}

	public Double getUserHeading() {
		return userHeading; 
	}

	private void convertFromUserHeadingToInternalHeadingInRadian() {
		Double internalHeading = 0.0;
		
		if (userHeading >= 0 && userHeading < 90) {
			// first quadrant
			internalHeading = 90 - userHeading;
		} else if (userHeading >= 90 && userHeading < 180) {
			// fourth quadrant
			internalHeading = -(userHeading - 90);
		} else if (userHeading < 0 && userHeading > -90) {
			// second quadrant
			internalHeading = 90 + Math.abs(userHeading);
		} else if (userHeading <= -90 && userHeading >= -180) {
			internalHeading = 90 + Math.abs(userHeading);
		}
		 
		Double userHeadingInRadian = internalHeading * RadiansPerDegree;
		// adjust the rotation by PI/2
		setHeadingInRadian(userHeadingInRadian);
		System.out.println("Setting new internal heading:" + headingInRadian);
	} 
	
	public void reset() {
		lastLongitude = -74.0059;
		lastLatitude = 40.7127;
		currentLongitude = -74.0059;
		currentLatitude = 40.7127; 
	}
}
