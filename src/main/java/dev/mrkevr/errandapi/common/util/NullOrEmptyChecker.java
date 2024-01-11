package dev.mrkevr.errandapi.common.util;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NullOrEmptyChecker {
	
	/*
	 * Returns true if all the objects are either null or empty
	 */
	public static boolean allNullOrEmpty(Object... objects) {
		for (Object obj : objects) {
			if (!isNullOrEmpty(obj)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNullOrEmpty(Object object) {
		
		if (object == null) {
			return true;
		}

		if (object instanceof String) {
			return ((String) object).isEmpty();
		}

		if (object instanceof Collection) {
			return ((Collection<?>) object).isEmpty();
		}

		// Add more checks for other data types if needed

		return false; // Default case: not null and not empty
	}
}
