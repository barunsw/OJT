package com.barunsw.ojt.gtkim.day09;

public class DP_PersonCollectionIerator implements DP_Iterator {

	private DP_PersonCollection personCollection;
	private int index;
	
	public DP_PersonCollectionIerator(DP_PersonCollection personCollection) {
		this.personCollection = personCollection;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext() {
		if(index < personCollection.getLength()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public Object next() {
		PersonVO onePerson = personCollection.getPersonAt(index);
		index++;
		return onePerson;
	}
}
