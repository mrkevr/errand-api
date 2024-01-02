package dev.mrkevr.errandapi.errand.exception;

import dev.mrkevr.errandapi.common.exception.ResourceNotFoundException;

public class ErrandNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = 1L;

	public ErrandNotFoundException() {
		super("Errand not found");
	}

	public ErrandNotFoundException(String message) {
		super(message);
	}
}
