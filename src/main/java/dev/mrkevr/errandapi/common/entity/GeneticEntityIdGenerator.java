package dev.mrkevr.errandapi.common.entity;

import java.security.SecureRandom;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeneticEntityIdGenerator implements IdentifierGenerator {

	private static final long serialVersionUID = 1L;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		long randomTwelveDigitNumber = generateRandomTwelveDigitNumber();
		return String.valueOf(randomTwelveDigitNumber);
	}

	private long generateRandomTwelveDigitNumber() {
		SecureRandom secureRandom = new SecureRandom();
		long randomTwelveDigitNumber = secureRandom.nextLong();
		randomTwelveDigitNumber = Math.abs(randomTwelveDigitNumber) % 1_000_000_000_000L; // Ensure 12 digits
		return randomTwelveDigitNumber;
	}
}
