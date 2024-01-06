package dev.mrkevr.errandapi.common.entity;

import java.security.SecureRandom;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeneticEntityIdGenerator implements IdentifierGenerator {

	private static final long serialVersionUID = 1L;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		String prefix = ((GenericEntity) object).getIdPrefix();
		int randomEightDigitNumber = new SecureRandom().nextInt(90000000) + 10000000;
		return prefix + String.valueOf(randomEightDigitNumber);
	}
}
