package edu.dtcc.csc241.model;

import java.util.Observable;
import java.util.Observer;

public class Statistician implements Observer {

	@Override
	public void update(Observable anObservable, Object observerableArgument) {

		total += (Double) observerableArgument;
		sampleCount++; 
		if (anObservable instanceof Customer) {
			System.out.println("Statistics - updating Customer observations: " + observerableArgument);
		} else {
			System.out.println("Statistics - updating Server observations:" + observerableArgument);
		}
	}
	
	int sampleCount;
	Double total = 0.0;
	public int getSampleCount() {
		return sampleCount;
	}

	public Double getTotal() {
		return total;
	}
	
	public Double getMean() {
		if (sampleCount > 0) {
			return (total / sampleCount); 
		}
		return 0.0;
	}  
}
