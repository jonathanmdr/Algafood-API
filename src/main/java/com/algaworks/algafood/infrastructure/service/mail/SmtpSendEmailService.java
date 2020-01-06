package com.algaworks.algafood.infrastructure.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.mail.EmailProperties;
import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.infrastructure.service.mail.exception.EmailException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpSendEmailService implements SendEmailService {
	
	private static final boolean BODY_FORMAT_ACCEPT_HTML = true; 
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfiguration;

	@Override
	public void send(Message message) {
		try {
			String body = processingTemplate(message);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setTo(message.getRecipients().toArray(new String[0]));
			helper.setFrom(emailProperties.getFrom());
			helper.setSubject(message.getSubject());
			helper.setText(body, BODY_FORMAT_ACCEPT_HTML);
			
			
			mailSender.send(mimeMessage);
		} catch(Exception ex) {
			throw new EmailException("Não foi possível enviar e-mail!", ex);
		}
	}
	
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		String corpo = processingTemplate(message);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getFrom());
		helper.setTo(message.getRecipients().toArray(new String[0]));
		helper.setSubject(message.getSubject());
		helper.setText(corpo, BODY_FORMAT_ACCEPT_HTML);
		
		return mimeMessage;
	}
	
	protected String processingTemplate(Message message) {
		try {
			Template template = freemarkerConfiguration.getTemplate(message.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
		} catch(Exception ex) {
			throw new EmailException("Não foi possível processar o template do e-mail!", ex);
		}		
	}

}
