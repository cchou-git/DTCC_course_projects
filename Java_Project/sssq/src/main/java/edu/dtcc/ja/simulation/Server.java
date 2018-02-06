package edu.dtcc.ja.simulation;

import java.util.Observable;

public class Server extends Observable {
	// OVERVIEW: Server is to model a generic serving entity. E.g., a supermarket cashier,
	//           or a gas station pumper, or a drive-through fast food station server.
	
	static final int BUSY_STATUS = 1;
	static final int IDLE_STATUS = 0;
	
	int status;
	Double lastTimeServeBecomeBusy = 0.0;
	
	public Server() {
		/**
		 * EFFECTS: Initializes a new Server.
		 */
		status = IDLE_STATUS;
	}
	
	public int getStatus() {
		/**
		 * EFFECTS: Returns the status of this server.
		 */
		return status;
	}
	
	/**
	 * tchou modified - need to record time.
	 * @param aStatus
	 * @param aTime
	 */
	public void setStatus(int aStatus, Double aTime) {
		/**
		 * MODIFIES: Modifies the status of the server to aStatus.
		 */
		if (status == IDLE_STATUS && aStatus == BUSY_STATUS) {
			lastTimeServeBecomeBusy = aTime;
		} else if (status == BUSY_STATUS && aStatus == IDLE_STATUS) {
			Double busyTime = aTime - lastTimeServeBecomeBusy;
			this.setChanged();
			this.notifyObservers(busyTime);
		}
		status = aStatus;
	} 
	
	/*
	 * tchou modified
	 */
	public void beginToService(Customer aCustomer, Double aTime) {
		/**
		 * MODIFIES: Make aCustomer the customer this server is serving.
		 *           Also mark the server to be busy.
		 */
		
		setStatus(BUSY_STATUS, aTime);		
	}
	
	public boolean thereAreCustomerWaiting(WaitingLine theLine) {
		/**
		 * EFFECTS: Check to see if there are customers waiting in the waiting line.
		 */
		
		return theLine.size() > 0;
	} 
}
