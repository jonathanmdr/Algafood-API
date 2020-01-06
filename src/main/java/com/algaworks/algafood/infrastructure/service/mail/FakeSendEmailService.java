package com.algaworks.algafood.infrastructure.service.mail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSendEmailService extends SmtpSendEmailService {
	
	@Override
	public void send(Message message) {
		String body = processingTemplate(message);
		log.info("[FAKE E-MAIL] Para: {}\n{}", message.getRecipients(), body);
	}

}
