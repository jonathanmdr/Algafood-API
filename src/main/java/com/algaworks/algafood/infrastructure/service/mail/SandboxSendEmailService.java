package com.algaworks.algafood.infrastructure.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.mail.EmailProperties;

public class SandboxSendEmailService extends SmtpSendEmailService {
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		MimeMessage mimeMessage = super.createMimeMessage(message);
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setTo(emailProperties.getSandBox().getRecipient());
		
		return mimeMessage;
	}

}
