package com.algaworks.algafood.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.infrastructure.service.mail.FakeSendEmailService;
import com.algaworks.algafood.infrastructure.service.mail.SandboxSendEmailService;
import com.algaworks.algafood.infrastructure.service.mail.SmtpSendEmailService;

@Configuration
public class EmailConfig {
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public SendEmailService sendEmailService() {
		switch (emailProperties.getImpl()) {
		case FAKE:
			return new FakeSendEmailService();
		case SMTP:
			return new SmtpSendEmailService();
		case SANDBOX:
			return new SandboxSendEmailService();
		default:
			return null;
		}
	}

}
