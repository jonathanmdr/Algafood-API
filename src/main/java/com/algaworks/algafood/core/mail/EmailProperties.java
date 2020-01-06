package com.algaworks.algafood.core.mail;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	
	private Implementation impl = Implementation.FAKE;
	
	@NotNull
	private String from;
	
	private SandBox sandBox = new SandBox();
	
	public enum Implementation {
		SMTP,
		FAKE,
		SANDBOX
	}
	
	@Getter
	@Setter
	public class SandBox {
		
		private String recipient;
		
	}

}
