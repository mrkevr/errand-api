package dev.mrkevr.errandapi.library.entity;

import java.security.SecureRandom;
import java.util.Random;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeneticEntityIdGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		String prefix = ((GenericEntity) object).getIdPrefix();
		int randomSixDigitNumber = new SecureRandom().nextInt(900_000) + 100_000;
		return prefix + String.valueOf(randomSixDigitNumber);
	}
}
