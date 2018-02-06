package edu.dtcc.ja.simulation;

public class Event {
	// OVERVIEW: An Event is a an activity happens at a point in time.
	
	
	Double eventTime;
	static final int ARRIVAL_EVENT = 1;
	static final int END_OF_SERVICE_EVENT = 2;
	int eventType;
	Object eventEntity;
	
	
	public Event() {
		/**
		 * EFFECTS: Initializes a new Event.
		 */
	}
	
	public Double getEventTime() {
		/**
		 * EFFECTS: Return the time of this event.
		 */
		
		return eventTime;
	}
	
	public void setEventTime(Double aTime) {
		/**
		 * MODIFIES: the time of this event
		 * EFFECTS: Sets the time of this event.
		 */
		eventTime = aTime;
	}
	
	public void setEventType(int aType) {
		/**
		 * MODIFIES: the type of this event
		 * EFFECTS: Sets the type of this event.
		 */
		eventType = aType;
	}
	
	public int getEventType() {
		/**
		 * EFFECTS: Returns the type of this event.
		 */
		return eventType;
	} 
	
	public Object getEventEntity() {
		/**
		 * EFFECTS: Returns the entity associated with this event.
		 */
		return eventEntity;
	}
	
	public Object setEventEntity(Object anObject) {
		/**
		 * EFFECTS: Set the entity associated with this event to anObject.
		 */
		eventEntity = anObject;
		return anObject;
	}
}
