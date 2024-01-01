package dev.mrkevr.errandapi.common.entity;

import java.security.SecureRandom;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeneticEntityIdGenerator implements IdentifierGenerator {

	private static final long serialVersionUID = 1L;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		String prefix = ((GenericEntity) object).getIdPrefix();
		int randomSixDigitNumber = new SecureRandom().nextInt(900_000) + 100_000;
		return prefix + String.valueOf(randomSixDigitNumber);
	}
}
