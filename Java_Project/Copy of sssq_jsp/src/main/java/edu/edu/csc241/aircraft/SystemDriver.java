package edu.edu.csc241.aircraft;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class SystemDriver implements Observer, Runnable {
	
	private ScheduledTask flyingTask = null;
	
	Airplane TheAirplane;
	
	Double systemTime;
	
	Thread runtimeThread;
	
	KeyboardObservable TheKeyboard;
	
	public void initSystem(int tickMillSeconds, Double initSpeed, Double initHeading) {
		systemTime = (Double) (System.currentTimeMillis() * 1.0);
		flyingTask = new ScheduledTask(tickMillSeconds, systemTime);
		TheAirplane = new Airplane(systemTime);
		TheAirplane.setMilesPerHour(initSpeed);
		TheAirplane.setUserHeading(initHeading);
		flyingTask.addObserver(TheAirplane);
		TheKeyboard = new KeyboardObservable();
		TheKeyboard.addObserver(this);
		TheKeyboard.setup();
		runtimeThread = new Thread(flyingTask);  
	}
	 
	
	public void update() {
		TheKeyboard.isWPressed();
	}
	
	public static void main(String args[]) {
		SystemDriver driver = new SystemDriver();
		
		driver.initSystem(1000, 600.0, 45.0); 
		Thread t = new Thread(driver);
		t.start();
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

	@Override
	public void run() {
		if (runtimeThread != null) {
			runtimeThread.start();
			while (true) { 
				try { 
					Thread.sleep(1000); 
					update();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
	}
}
