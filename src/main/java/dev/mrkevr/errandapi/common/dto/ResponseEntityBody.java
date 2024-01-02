package dev.mrkevr.errandapi.common.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseEntityBody {

	String title;
	String status;
	LocalDateTime timeStamp;
	Object body;
	
	public static ResponseEntityBody of(String title, HttpStatus httpStatus, Object body) {
		return new ResponseEntityBody(title, httpStatus.toString(), LocalDateTime.now(), body);
	}

	private ResponseEntityBody(String title, String status, LocalDateTime timeStamp, Object body) {
		this.title = title;
		this.status = status;
		this.timeStamp = timeStamp;
		this.body = body;
	}
}