package edu.dtcc.ja.simulation;

public class Scheduler {
	// OVERVIEW: A Scheduler is to schedule the "arrivals" and "end of service" events in
	//           the "study."
	
	public Scheduler() {
		/**
		 * EFFECTS: Initializes a new Scheduler.
		 */
	}
	
	public Event scheduleNextCustomerArrival(Double now, Double anInterval) {
		/**
		 * EFFECTS: Schedule the next arrival of customer.
		 */
		
		Event newEvent = new Event();
		
		newEvent.setEventEntity(new Customer());
		newEvent.setEventTime(now + anInterval);
		newEvent.setEventType(Event.ARRIVAL_EVENT);
		
		return newEvent; 
	}
	
	// note -- tchou added aServer to this spec
	public Event scheduleNextEndOfService(Double now, Double anInterval, Server aServer) {
		/**
		 * EFFECTS: Schedule and return the next end of service event.
		 */
		
		Event newEvent = new Event();
		
		newEvent.setEventEntity(aServer);
		newEvent.setEventTime(now + anInterval);
		newEvent.setEventType(Event.END_OF_SERVICE_EVENT);
		
		return newEvent;
	}

}
