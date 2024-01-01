package dev.mrkevr.errandapi.user.exception;

import dev.mrkevr.errandapi.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User not found");
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
