package dev.mrkevr.errandapi.errand.api;

public enum ErrandCategory {

	GROCERY_SHOPPING("Grocery Shopping"),
	FOOD_DELIVERY("Food Delivery"),
	PACKAGE_DELIVERY("Package Delivery"),
	LAUNDRY_SERVICES("Laundry Services"),
	PET_SERVICES("Pet Services"),
	BANKING_TASKS("Banking Tasks"),
	HOME_SERVICES("Home Services"),
	ADMINISTRATIVE_TASKS("Administrative Tasks"),
	BOOKING_AND_RESERVATION("Booking and Reservation"),
	MISCELLANEOUS("Miscellanaeous");
	
	private String string;
	
	private ErrandCategory(String string){
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}
}
