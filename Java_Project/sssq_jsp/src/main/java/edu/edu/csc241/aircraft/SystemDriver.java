package edu.edu.csc241.aircraft;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class SystemDriver implements Observer , Runnable {
	
	private ScheduledTask flyingTask = null;
	
	Airplane TheAirplane;
	
	Double systemTime;
	
	Thread runtimeThread; 
	
	public Double getInitialSpeed() {
		return initialSpeed;
	}


	public void setInitialSpeed(Double initialSpeed) {
		this.initialSpeed = initialSpeed;
	}


	public Double getInitialHeading() {
		return initialHeading;
	}


	public void setInitialHeading(Double initialHeading) {
		this.initialHeading = initialHeading;
	}

	Double initialSpeed;
	Double initialHeading;
	
	public void initSystem(int tickMillSeconds, Double initSpeed, Double initHeading) {
		systemTime = (Double) (System.currentTimeMillis() * 1.0);
		flyingTask = new ScheduledTask(tickMillSeconds, systemTime);
		TheAirplane = new Airplane(systemTime);
		setInitialSpeed(initSpeed);
		setInitialHeading(initHeading);
		TheAirplane.setMilesPerHour(initSpeed);
		TheAirplane.setUserHeading(initHeading);
		flyingTask.addObserver(TheAirplane); 
		runtimeThread = new Thread(flyingTask);  
		
	} 
	
	public static void main(String args[]) {
		SystemDriver driver = new SystemDriver(); 
		driver.initSystem(1000, 600.0, -135.0);  
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// The arg is the key that is pressed.
		
		Integer keyCode = (Integer) arg;
		if (keyCode != null) {
			if (keyCode.intValue() == KeyEvent.VK_LEFT) {
				Double currentHeading = TheAirplane.getUserHeading();
				TheAirplane.setUserHeading(currentHeading--);
			} else if (keyCode.intValue() == KeyEvent.VK_RIGHT) {
				Double currentHeading = TheAirplane.getUserHeading();
				TheAirplane.setUserHeading(currentHeading++);
			}	 
		}
	} 
	 
	
	public void setHeading(Double userHeadingInDegree) {
		TheAirplane.setUserHeading(userHeadingInDegree);		
	}
	
	public void resetAirplane() {
		TheAirplane.setMilesPerHour(initialSpeed);
		TheAirplane.setUserHeading(initialHeading);
		TheAirplane.reset();
	}
	
	public void setSpeed(Double aSpeed) {
		TheAirplane.setMilesPerHour(aSpeed);
	}
	
	public String getStatus() {
		return TheAirplane.getData();
	}
	
	public Airplane getAirplane() { 
		return TheAirplane;
	}


	@Override
	public void run() {
		runtimeThread.run();
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	} 
}
