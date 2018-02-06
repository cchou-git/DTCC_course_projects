package edu.dtcc.ja.simulation;

import java.util.ArrayList;

public class WaitingLine {
		// OVERVIEW: WaitingLine is used to model a real-life waiting line in a supermarket or 
	    //           any first-in, first-out waiting facility. 
		
	 	ArrayList<Customer> queue;
		
		/** 
		 * constructor
		 */
		public WaitingLine() {
			/**
			 * EFFECTS: Initializes a new WaitingLine.
			 */
			queue = new ArrayList<Customer>();
		}
		
		public int size() {
			/**
			 * EFFECTS: Returns the size of people in this waiting line.
			 */
			
			return queue.size();
		}
		
		public boolean hasRoom() {
			/**
			 * EFFECTS: Returns true if there are still room for people to wait.
			 */
			
			return true;  // assuming unlimited capacity
		}
		
		public Object getNextInLine() {
			/**
			 * EFFECTS: Gets and removes the first customer from the line.
			 */
			
			if (queue.size() > 0) {
				return queue.remove(0);
			} else {
				return null;
			}
		}
		
		public int addToEndOfLine(Object anObject) {
			/**
			 * REQUIRES: There should have enough room for anObject to join at the end of the line.
			 * MODIFIES: post this = this + anObject 
			 * EFFECTS: Add anObject to the contents.
			 */
			queue.add((Customer) anObject);
			return size();			
		} 
		
		
}
