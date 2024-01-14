package dev.mrkevr.errandapi.errand.api;

//public enum ErrandCategory {
//
//	GROCERY_SHOPPING("Grocery Shopping"),
//	FOOD_DELIVERY("Food Delivery"),
//	PACKAGE_DELIVERY("Package Delivery"),
//	LAUNDRY_SERVICES("Laundry Services"),
//	PET_SERVICES("Pet Services"),
//	BANKING_TASKS("Banking Tasks"),
//	HOME_SERVICES("Home Services"),
//	ADMINISTRATIVE_TASKS("Administrative Tasks"),
//	BOOKING_AND_RESERVATION("Booking and Reservation"),
//	MISCELLANEOUS("Miscellanaeous");
//	
//	private String string;
//	
//	private ErrandCategory(String string){
//		this.string = string;
//	}
//	
//	@Override
//	public String toString() {
//		return string;
//	}
//	
//}

public enum ErrandCategory {

	GROCERY_SHOPPING,
	FOOD_DELIVERY,
	PACKAGE_DELIVERY,
	LAUNDRY_SERVICES,
	PET_SERVICES,
	BANKING_TASKS,
	HOME_SERVICES,
	ADMINISTRATIVE_TASKS,
	BOOKING_AND_RESERVATION,
	MISCELLANEOUS;
	
	@Override
	public String toString() {
		switch (this) {
		case GROCERY_SHOPPING: {
			return "Grocery Shopping";
		}
		case FOOD_DELIVERY: {
			return "Food Delivery";
		}
		case PACKAGE_DELIVERY: {
			return "Package Delivery";
		}
		case LAUNDRY_SERVICES: {
			return "Laundry Services";
		}
		case PET_SERVICES: {
			return "Pet Services";
		}
		case BANKING_TASKS: {
			return "Banking Tasks";
		}
		case HOME_SERVICES: {
			return "Home Services";
		}
		case ADMINISTRATIVE_TASKS: {
			return "Administrative Tasks";
		}
		case BOOKING_AND_RESERVATION: {
			return "Booking and Reservation";
		}
		case MISCELLANEOUS: {
			return "Miscellaneous";
		}
		default:
			return null;
		}
	}
	
}
