package edu.dtcc.csc241.chap3;

 
import java.util.ArrayList;

import edu.dtcc.csc241.model.Event;

public class QuickSort {
	
	public final int START_FROM_LEFT = 1;
	public final int START_FROM_RIGHT = 2;
	
	
	private void swap(ArrayList<Event> list, int pos1, int pos2) {
		Event temp = list.get(pos1);
		list.set(pos1, list.get(pos2));
		list.set(pos2, temp); 
	} 
	
	
	/**
	 * PRE - it starts with pivot element on the left
	 * POST - the pivot element is at the correct index
	 * @param list
	 * @param indexOne
	 * @param indexTwo
	 * @return
	 */
	private int partitionArray(ArrayList<Event> list, int indexOne, int indexTwo) {
		int pivotIndex = indexOne;    
		int startIndex = indexTwo; 
		int direction = START_FROM_RIGHT;  
		
		while ((direction == START_FROM_RIGHT && startIndex > pivotIndex) || 
			   (direction == START_FROM_LEFT  && startIndex < pivotIndex)) {
			if (direction == START_FROM_RIGHT) {
				// pivot element is on the left hand side of the array
				for (int i = startIndex; i >= pivotIndex; i--) {
					if (list.get(pivotIndex).getEventTime() >= list.get(i).getEventTime()) { 
						swap(list, pivotIndex, i); 
						// rearrange the pivot index, starting index, and direction
						// since the swap just took place
						startIndex = pivotIndex + 1;
						pivotIndex = i;
						direction = START_FROM_LEFT;   
						break;
					}
				}
			} else {
				// pivot element is on the right hand side of the array
				for (int i = startIndex; i <= pivotIndex; i++) {
					if (list.get(pivotIndex).getEventTime() <= list.get(i).getEventTime()) { 
						swap(list, pivotIndex, i); 
						// rearrange the pivot index, starting index, and direction
						// since the swap just took place
						startIndex = pivotIndex - 1;
						pivotIndex = i;
						direction = START_FROM_RIGHT;  
						break;
					}
				}
			}
		} 
		return pivotIndex; // this index is changed potentially many times....
	}
	
	public int sortIntArray(ArrayList<Event> list, int lowIndex, int hiIndex) {
		int pivotIndex = partitionArray(list, lowIndex, hiIndex);
		if ((pivotIndex - 1) >= lowIndex)
			pivotIndex = sortIntArray(list, lowIndex, pivotIndex - 1);
		if ((pivotIndex + 1) <= hiIndex)
			pivotIndex = sortIntArray(list, pivotIndex + 1, hiIndex);
		return pivotIndex;
	}  
}