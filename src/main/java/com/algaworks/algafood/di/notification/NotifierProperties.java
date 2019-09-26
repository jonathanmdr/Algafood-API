package com.algaworks.algafood.di.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notifier.email")
public class NotifierProperties {

	private String hostServer;
	private Integer portServer;	
	
	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}
	
	public void setPortServer(Integer portServer) {
		this.portServer = portServer;
	}

	public String getHostServer() {
		return hostServer;
	}

	public Integer getPortServer() {
		return portServer;
	}	
	
}
