package edu.fau.group4.donateme;

import java.util.Comparator;

public class CustomComparator implements Comparator<RequestObject> {

	@Override
	public int compare(RequestObject o1, RequestObject o2){
		return Double.compare(o1.distance, o2.distance);
	}
}
