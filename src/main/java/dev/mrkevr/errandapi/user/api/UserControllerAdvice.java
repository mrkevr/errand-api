package dev.mrkevr.errandapi.user.api;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.mrkevr.errandapi.user.exception.UserNotFoundException;

@RestControllerAdvice
class UserControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
		System.out.println(this.getClass().getName());
		ProblemDetail problemDetail = createProblemDetail(ex, HttpStatus.NOT_FOUND);
		return ResponseEntity.of(problemDetail).build();
	}

	/*
	 * Helper method to create ProblemDetail object
	 */
	private ProblemDetail createProblemDetail(Exception ex, HttpStatus status) {
		
		
		
		ProblemDetail problemDetail = ProblemDetail.forStatus(status);
		problemDetail.setProperty("errors", ex.getMessage());
		problemDetail.setProperty("timeStamp", LocalDateTime.now());
		return problemDetail;
	}
}
