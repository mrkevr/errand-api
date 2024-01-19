package dev.mrkevr.errandapi.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MvcControllerAdvice {

	@Value("${application.mvc.name}")
	private String applicationName;

	@ModelAttribute("applicationName")
	public String applicationName() {
		return this.applicationName;
	}
}
