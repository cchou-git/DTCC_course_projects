
public class SystemDriver {

	public static void main(String[] args) {
		double systemTime = (double) System.currentTimeMillis();
		ScheduledTask updTask = new ScheduledTask(1000, systemTime);
		Airplane anAirplane = new Airplane(systemTime);
		updTask.addObserver(anAirplane);
		Thread t = new Thread(updTask); 
		
		t.start();
		
		while (updTask.isRunning()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Main program waking up!");
		}
	}

}
