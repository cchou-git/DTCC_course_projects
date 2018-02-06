import java.util.Observable;

// Create a class extends with TimerTask
public class ScheduledTask extends Observable implements Runnable {

	private boolean running = true;
	int numberOfMilliSeconds;
	Double currentTime;
	Double lastTime;

	protected boolean tick() {
		this.setChanged();
		this.notifyObservers(currentTime);
		return true;
	}

	public ScheduledTask(int tickIntervalInMilliSeconds, double currentTime) {
		numberOfMilliSeconds = tickIntervalInMilliSeconds;
		this.currentTime = currentTime;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	// Add your task here
	public void run() {
		while (isRunning()) {
			try {
				lastTime = currentTime;
				Thread.sleep(numberOfMilliSeconds);
				currentTime = (double) System.currentTimeMillis();
				tick();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
