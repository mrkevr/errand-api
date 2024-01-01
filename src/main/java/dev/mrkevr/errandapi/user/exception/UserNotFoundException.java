package dev.mrkevr.errandapi.user.exception;

import dev.mrkevr.errandapi.library.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

	public UserNotFoundException() {
		super("User not found");
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
