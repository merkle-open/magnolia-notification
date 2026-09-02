package com.merkle.oss.magnolia.notification.service;

import info.magnolia.module.mail.MailModule;
import info.magnolia.module.mail.MgnlMailFactory;
import info.magnolia.module.mail.smtp.SmtpConfiguration;
import info.magnolia.module.mail.smtp.authentication.SmtpAuthentication;
import info.magnolia.module.mail.templates.MailAttachment;
import info.magnolia.module.mail.templates.MgnlEmail;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machinezoo.noexception.Exceptions;
import com.merkle.oss.magnolia.notification.NotificationTriggerModule;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class EmailService {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final Provider<MailModule> mailModuleProvider;
    private final NotificationTriggerModule notificationTriggerModule;

    @Inject
	public EmailService(
			final Provider<MailModule> mailModuleProvider,
			final NotificationTriggerModule notificationTriggerModule
	) {
		this.mailModuleProvider = mailModuleProvider;
        this.notificationTriggerModule = notificationTriggerModule;
    }

	public void send(final String subjectTemplate, final String bodyTemplate, final Collection<String> to, final Map<String, Object> parameters, final Collection<File> attachments) {
		final MailModule mailModule = mailModuleProvider.get();
		final MgnlMailFactory mailFactory = mailModule.getFactory();
		final String from = Optional
				.ofNullable(notificationTriggerModule.getSender())
				.or(() -> Optional.of(mailModule.getSmtpConfiguration()).map(SmtpConfiguration::getAuthentication).map(SmtpAuthentication::getUser))
				.orElseThrow(() ->
						new NullPointerException("mail sender not present!")
				);
		Exceptions.wrap().run(() -> mailModule.getHandler().sendMail(create(mailFactory, subjectTemplate, bodyTemplate, from, to, parameters, attachments)));
	}

	private MgnlEmail create(
		final MgnlMailFactory mailFactory,
		final String subjectTemplate,
		final String bodyTemplate,
		final String from,
		final Collection<String> to,
		final Map<String, Object> parameters,
		final Collection<File> attachments
	) throws Exception {
		LOG.debug("sending email from:{} to:{} parameters:{} bodyTemplate:{} subjectTemplate:{}" , from, to, parameters, bodyTemplate, subjectTemplate);
		final MgnlEmail email = mailFactory.getEmailFromType(parameters, "freemarker", null, Collections.emptyList());
		email.setFrom(from);
		email.setToList(String.join(";", to));
		email.setSubject(subjectTemplate);
		email.setBody(bodyTemplate);
		email.setAttachments(attachments.stream().map(attachment ->
			new MailAttachment(attachment, attachment.getName(), null, MailAttachment.DISPOSITION_ATTACHMENT)
		).toList());
		return email;
	}
}
