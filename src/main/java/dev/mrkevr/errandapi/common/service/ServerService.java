package dev.mrkevr.errandapi.common.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.mrkevr.errandapi.common.exception.ApiException;

/*
 * Solutions from https://stackoverflow.com/questions/38916213/how-to-get-the-spring-boot-host-and-port-address-during-run-time
 */
@Service
public class ServerService {
	
	@Autowired
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	@Autowired
	@Value("${server.port}")
	private int serverPort;

	public String getLocalHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new ApiException(e.getMessage());
		}
	}

	public String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new ApiException(e.getMessage());
		}
	}

	public String getRemoteHostAddress() {
		return InetAddress.getLoopbackAddress().getHostAddress();
	}

	public String getRemoteHostName() {
		return InetAddress.getLoopbackAddress().getHostName();
	}

	public String getBaseUri() {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			      .scheme("http")
			      .host(this.getRemoteHostName())
			      .port(serverPort)
			      .path(contextPath).build();
		
		return uriComponents.toUriString();
	}
}
