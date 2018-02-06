 
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;


public class Airplane implements Observer {
	Double x = 0.0;
	Double y = 0.0; 
	Double direction = 30.0;     // 0 - 180  
	  
	
	public Airplane(double aTime) {
	 
	}
	
	public Point2D getPosition() {
		return new Point2D.Double(x,y);
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
		sb.append("X = " + x);
		sb.append(" Y = " + y);
		sb.append(" Heading = " + headingInRadian + "(" + (headingInRadian / Math.PI) * 180.0 + ")");
		return sb.toString();
	}
	
	Double lastTime = null;
	
	Double lastX;
	Double lastY;
	Double milesPerHour = 500.0;
	Double heading = 120.0;
	Double milesPerLattitudeDegree = 69.0;
	Double milesPerLongitudeDegree = 69.172;
	private final Double SecondsInAnHour = 3600.0;

	Double headingInRadian = 0.0;
	
	void move(Double numberOfSeconds) {
		milesPerHour = 500.0;
		Double milesPerSecond = milesPerHour / SecondsInAnHour;
		
		Double distance = milesPerSecond * numberOfSeconds;
		headingInRadian = (heading / 180.0) * Math.PI;
		
		
		lastX = x;
		lastY = y;
		Double deltaX = (Math.cos(headingInRadian) * distance);
		Double deltaY = (Math.sin(headingInRadian) * distance); 
		y = y + deltaY;
		x = x + deltaX;
	}		
	 
}
