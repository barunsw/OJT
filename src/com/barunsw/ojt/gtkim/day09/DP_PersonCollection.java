package com.barunsw.ojt.gtkim.day09;

public class DP_PersonCollection implements DP_Aggregate {
	private PersonVO[] personList;
	private int last = 0;
	
	public DP_PersonCollection(int maxsize) {
		this.personList = new PersonVO[maxsize];
	}
	
	public PersonVO getPersonAt(int index) {
		return personList[index];
	}
	
	public void addPerson(PersonVO onePerson) {
		this.personList[last] = onePerson;
		last++;
	}
	
	public int getLength() {
		return last;
	}
	
	@Override
	public DP_Iterator iterator() {
		return new DP_PersonCollectionIerator(this);
	}
}
