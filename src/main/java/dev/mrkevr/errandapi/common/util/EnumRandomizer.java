package dev.mrkevr.errandapi.common.util;

import java.util.Random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumRandomizer {

	private static final Random RANDOM = new Random();

	/**
	 * Randomly selects and returns a value from the provided enum class.
	 *
	 * @param enumClass The enum class to randomize values from.
	 * @param <T>       The enum type.
	 * @return A randomly selected value from the enum.
	 * @throws IllegalArgumentException if the provided class is not an enum type.
	 */
	public static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass) {
		if (!enumClass.isEnum()) {
			throw new IllegalArgumentException(enumClass.getSimpleName() + " is not an enum type");
		}

		T[] enumValues = enumClass.getEnumConstants();
		int randomIndex = RANDOM.nextInt(enumValues.length);

		return enumValues[randomIndex];
	}
}
