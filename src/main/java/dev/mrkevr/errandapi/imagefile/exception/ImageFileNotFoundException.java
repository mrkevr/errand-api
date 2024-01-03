package dev.mrkevr.errandapi.imagefile.exception;

import dev.mrkevr.errandapi.common.exception.ResourceNotFoundException;

public class ImageFileNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = 1L;

	public ImageFileNotFoundException() {
		super("Image file not found");
	}

	public ImageFileNotFoundException(String message) {
		super(message);
	}
}
