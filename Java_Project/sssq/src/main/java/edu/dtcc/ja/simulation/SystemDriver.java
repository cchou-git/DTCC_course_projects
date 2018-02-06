package edu.dtcc.ja.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SystemDriver {
	
	// OVERVIEW: A SystemDriver drives the system. It is the guy who controls the interactions between
	//           Objects.
	//           The "system" in this exercise is to run through a "single-server-single-queue" system and
	//           to measure the following statistics:
	//           1. The time a customer spends in the "system" (e.g., thef gas station, the drive-through store)
	//           2. The time a customer waits in the queue if there is any.
	//           3. The percentage, over the period of pre-defined time, that the server is busy (or idle).


	// our servers
	Server theServer;
	Server theSecondServer;
	
	WaitingLine theWaitingLine;
	Scheduler theScheduler;
	ArrayList<Event> eventCalendar;
	Double currentTime;
	Double stopTime;
	String currentTimeString;
	
	public String getCurrentTimeString() {
		return currentTimeString;
	}

	public String getStopTimeOneString() {
		return stopTimeOneString;
	}

	public String getStopTimeTwoString() {
		return stopTimeTwoString;
	}
	
	//String stopTimeString;
	String stopTimeOneString;
	String stopTimeTwoString;
	
	//Statistician serverStatitician;

	Statistician serverStatiticianOne;
	Statistician serverStatiticianTwo;
	
	/*
	public Statistician getServerStatitician() {
		return serverStatitician;
	}
	*/

	public Statistician getServerStatiticianOne() {
		return serverStatiticianOne;
	}
	
	public Statistician getServerStatiticianTwo() {
		return serverStatiticianTwo;
	}
	
	Statistician customerStatitician;
	
	// add random arrival time between 8-12 seconds
	Double averageTimeBetweenArrival;
	public void setAverageTimeBetweenArrival(Double averageTimeBetweenArrival) {
		this.averageTimeBetweenArrival = averageTimeBetweenArrival;
	}

	public void setAverageServiceTime(Double averageServiceTime) {
		this.averageServiceTime = averageServiceTime;
	}

	Double averageServiceTime;
	
	public SystemDriver() {
		/**
		 * EFFECTS: Initializes a new SystemDriver.
		 * 
		 */
		
		initialize();
	}
	
	private void initialize() {
		/**
		 * EFFECTS: Initialize and prepare the necessary objects/instances in the one-server-one-queue system. 
		 *          Instantiate all objects:
		 *          
		 *          	1. Create a Server.
		 *          	2. Create a WaitingLine.
		 *              3. Create a Scheduler.
		 *              3. Create an Event Collection - in order of event time ascending. 
		 *              4. Ask the scheduler to create the first arrival event.
		 */
		
		theServer = new Server();
		theSecondServer = new Server();
		
		theWaitingLine = new WaitingLine();
		theScheduler = new Scheduler();
		eventCalendar = new ArrayList<Event>();	   // discussion point
		currentTime = 0.0;
		stopTime = 200.0;
		//SystemDriver.serverStatitician = new Statistician();
		serverStatiticianOne = new Statistician();
		serverStatiticianTwo = new Statistician();
		customerStatitician = new Statistician();
		
		// server observers
		//theServer.addObserver(serverStatitician);
		theServer.addObserver(serverStatiticianOne);
		theServer.addObserver(serverStatiticianTwo);
		//theSecondServer.addObserver(serverStatiticianTwo);
	}
	
	public void setupSystemRunTimeTo(Double aStopTimeInSeconds) {
		/**
		 * MODIFIES: Set the stop system time (in seconds).
		 */
		stopTime = aStopTimeInSeconds;
	}
	
	public void start() {
		/**
		 * EFFECTS: Start the system up by scheduling the first arrival of customer. 
		 *          Then go to the loop of processing events.
		 *          As long as we are not done, process the next event.
		 */
		
		//Event anEvent = theScheduler.scheduleNextCustomerArrival(currentTime, 50.0);
		Event anEvent = theScheduler.scheduleNextCustomerArrival(currentTime, randomArrivalTime);
		eventCalendar.add(anEvent);
		Customer theCustomer = (Customer) anEvent.getEventEntity();
		theCustomer.addObserver(customerStatitician);
		
		while (false == areWeDoneYet()) {
			processNextEvent();
		} 	
		
		// Set servers to idle
		theServer.setStatus(Server.IDLE_STATUS, stopTime);
		theSecondServer.setStatus(Server.IDLE_STATUS, stopTime);
		
		double meanTime = customerStatitician.getMean();
		double totalTimeOne = serverStatiticianOne.getTotal() / 200.0 * 100.0;
		double totalTimeTwo = serverStatiticianTwo.getTotal() / 200.0 * 100.0;

		System.out.println("Average waiting time in queue:" + customerStatitician.getMean());
		System.out.println("Average percentage busy:" + (serverStatiticianOne.getTotal() / 200.0) * 100.0);
		System.out.println("Average percentage busy:" + (serverStatiticianTwo.getTotal() / 200.0) * 100.0);
		
		currentTimeString = Double.toString(meanTime);
		stopTimeOneString = Double.toString(totalTimeOne);
		stopTimeTwoString = Double.toString(totalTimeTwo);
		
	}
	
	/**
	 * tchou - added 9/29/2015
	 * Note - This can be replaced by available Java class but I am using it since we invented it.
	 */
	private void sortEvents() {
		QuickSort mySorter = new QuickSort();
		mySorter.sortIntArray(eventCalendar, 0, eventCalendar.size() - 1);
	}
	
	/**
	 * tchou modified to add an argument - theEvent.
	 */
	private void advanceClockToNextEvent(Event theEvent) {
		/**
		 * EFFECTS: Advance system time to the next event's time.
		 */
		currentTime = theEvent.getEventTime();		
	}
	
	private void processNextEvent() {
		/**
		 * EFFECTS: Drive through the available events one by one and advance the system time to the event time.
		 *        
		 */
		Event anEvent = eventCalendar.remove(0);
		advanceClockToNextEvent(anEvent);
		if (anEvent.getEventType() == Event.ARRIVAL_EVENT) {
			processNextArrivalEvent(anEvent);
		} else if (anEvent.getEventType() == Event.END_OF_SERVICE_EVENT) {
			processNextEndOfServiceEvent(anEvent);
		}
	}
	
	private void processNextArrivalEvent(Event anEvent) {
		/**
		 * EFFECTS: Advance the current time to the time noted by anEvent and process as follows:
		 * 
		 *          Work out the cases described as follows:
		 *          WHEN a customer comes to the system
		 *               he or she will check whether the server is busy or not
		 *               
		 *               CASE 1 - the server is not busy
		 *                        the customer is being served - ask the server to serve him or her
		 *                        ask the scheduler to schedule an end of service event for this customer
		 *               CASE 2 - the server is busy
		 *                        the customer can't be served - enter the waiting line and wait  
		 */
				
		Event nextArrivalEvent = theScheduler.scheduleNextCustomerArrival(currentTime, 50.0);
		Customer aCustomer = (Customer) nextArrivalEvent.getEventEntity();
		aCustomer.addObserver(customerStatitician);
		eventCalendar.add(nextArrivalEvent);
		sortEvents();
		
		Customer theCustomer = (Customer) anEvent.getEventEntity();
		
		//if (theServer.getStatus() == Server.IDLE_STATUS && theSecondServer.getStatus() == Server.IDLE_STATUS) 
		
		if (theServer.getStatus() == Server.IDLE_STATUS && theSecondServer.getStatus() == Server.IDLE_STATUS ) {
			theServer.beginToService(theCustomer, currentTime);
			Event nextEndofServiceEvent = theScheduler.scheduleNextEndOfService(currentTime, 60.0, theServer); 
			eventCalendar.add(nextEndofServiceEvent);
			sortEvents();
		} 
		else if (theServer.getStatus() == Server.BUSY_STATUS) {
			theSecondServer.beginToService(theCustomer, currentTime);
			Event nextEndofServiceEvent = theScheduler.scheduleNextEndOfService(currentTime, 60.0, theSecondServer); 
			eventCalendar.add(nextEndofServiceEvent);
			sortEvents();
		}
		else if (theSecondServer.getStatus() == Server.BUSY_STATUS) {
			theServer.beginToService(theCustomer, currentTime);
			Event nextEndofServiceEvent = theScheduler.scheduleNextEndOfService(currentTime, 60.0, theServer); 
			eventCalendar.add(nextEndofServiceEvent);
			sortEvents();
		}
		else {
			theCustomer.joinAtEndOfWaitingLine(theWaitingLine, currentTime); 
		} 
	}
	
	private void processNextEndOfServiceEvent(Event anEvent) {
		/**
		 * EFFECTS: Advance the current time to the time noted by anEvent and process as follows:
		 * 
		 *               WHEN a customer is finishing service (i.e., end of service event takes place)
		 *            
		 *               CASE 1 - the server checks to see if there are still customers in the waiting line
		 *               		  IF there are still customers in the waiting line THEN
		 *                              ask the server to beginning serving the customer
		 *                              ask the scheduler to scheduler an end of service event for this customer
		 *                        IF there are no more customers in the waiting line THEN
		 *                              ask the server to take a break 
		 */
		
		Customer nextCustomer = (Customer) theWaitingLine.getNextInLine();
		if (nextCustomer != null && theServer.getStatus() == Server.BUSY_STATUS) {
			nextCustomer.calculateTimeSpentInWaiting(currentTime);
			theServer.beginToService(nextCustomer, currentTime);
			Event nextEndofServiceEvent = theScheduler.scheduleNextEndOfService(currentTime, 60.0, theServer); 
			eventCalendar.add(nextEndofServiceEvent);
			sortEvents();
		} else if (nextCustomer != null && theSecondServer.getStatus() == Server.BUSY_STATUS) {
			nextCustomer.calculateTimeSpentInWaiting(currentTime);
			theSecondServer.beginToService(nextCustomer, currentTime);
			Event nextEndofServiceEvent = theScheduler.scheduleNextEndOfService(currentTime, 60.0, theSecondServer); 
			eventCalendar.add(nextEndofServiceEvent);
			sortEvents();
		} else {
			theServer.setStatus(Server.IDLE_STATUS, currentTime);
			theSecondServer.setStatus(Server.IDLE_STATUS, currentTime);
		}
		
		
	}
	
	private boolean areWeDoneYet() {
		/**
		 * EFFECTS: Returns true if it is the end of system time. Else return false.
		 *          We are done when 
		 *          1. The end of pre-defined system time has reached.
		 *          2. There are no more events.
		 */
		
		return (currentTime > stopTime) || eventCalendar.size() < 1;
	}
	
	public void displaySystemState(boolean showEventFlag) {
		/**
		 * EFFECTS: Print out the current system state:
		 *           1. Number of customers in the WaitingLine.
		 *           2. Server utilization
		 * 	        If showEventFlag equals to true, also prints out the list of scheduled events.
		 */

	}
	
	static Random rn = new Random();
	static double randomArrivalTime = rn.nextInt(12-8+1) + 8;

	static double randomServiceTime = rn.nextInt(30-20+1) + 20;
	
	public double getrandomArrivelTime() {
		return randomArrivalTime;
	}
	
	public double getrandomeServiceTime() {
		return randomServiceTime;
	}
	
	
	public static void main(String args[]) {

		
		//Double timeBetween = 8.0;
		
		SystemDriver theDriver = new SystemDriver();
		theDriver.setupSystemRunTimeTo(200.0);
		//theDriver.setAverageTimeBetweenArrival(50.0);
		theDriver.setAverageTimeBetweenArrival(randomArrivalTime);
		//theDriver.setAverageServiceTime(60.0);
		theDriver.setAverageServiceTime(randomServiceTime);
		theDriver.start();
		
	}

}
