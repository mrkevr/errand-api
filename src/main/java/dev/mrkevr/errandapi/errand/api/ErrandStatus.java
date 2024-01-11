package dev.mrkevr.errandapi.errand.api;

public enum ErrandStatus {

	VACANT("Vacant"), 
	OCCUPIED("Occupied"), 
	CANCELLED("Vacant"), 
	COMPLETE("Complete");
	
	private String string;

	private ErrandStatus(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}
}
