package edu.dtcc.ja.simulation;

import java.util.Observable;

public class Customer extends Observable {
	// OVERVIEW: Customer is used to model a generic customer in a "queuing" application.
	
	long id;
	Double timeSpentInWaitingLine;
	static long TotalCount = 0;
	
	
	Double timeEnterWaitingLine;
	
	public Double getTimeEnterWaitingLine() {
		return timeEnterWaitingLine;
	}

	public void setTimeEnterWaitingLine(Double timeEnterWaitingLine) {
		this.timeEnterWaitingLine = timeEnterWaitingLine;
	}

	/** 
	 * constructor
	 */
	public Customer() {
		/**
		 * EFFECTS: Initializes a new Customer.
		 */
		timeSpentInWaitingLine = 0.0;
		id = TotalCount++;
	}
	
	public int getID() {
		/**
		 * EFFECTS: Return the internal ID of the customer.
		 */
		return 0;
	}
	
	public void calculateTimeSpentInWaiting(Double currentTimeInSeconds) {
		/**
		 * REQUIRES: currentTimeInSeconds must be greater than or equal to the time this customer 
		 *           enters the waiting line
		 * EFFECTS: calculate the time this customer spends in the queue
		 */
		timeSpentInWaitingLine = currentTimeInSeconds - timeEnterWaitingLine;
		this.setChanged();
		this.notifyObservers(timeSpentInWaitingLine);
	}
	
	// Note - tchou added one more argument... currentTimeInSeconds
	public boolean joinAtEndOfWaitingLine(WaitingLine aWaitingLine, Double currentTimeInSeconds) {
		/**
		 * REQUIRES: a WaitingLine to join
		 * MODIFIES: record the time this customer enters the waiting line
		 */
		
		aWaitingLine.addToEndOfLine(this);
		setTimeEnterWaitingLine(currentTimeInSeconds);
		return false;
	}
	
	/**
	 * tchou modified.
	 * @param aServer
	 * @param aTime
	 */
	public void startReceivingService(Server aServer, Double aTime) {
		/**
		 * REQUIRES: a Server
		 * MODIFIES:  
		 * EFFECTS:
		 */
		
		aServer.beginToService(this, aTime);
	}
}
	