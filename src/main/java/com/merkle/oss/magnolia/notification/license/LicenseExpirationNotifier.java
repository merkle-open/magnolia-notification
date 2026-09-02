package com.merkle.oss.magnolia.notification.license;

import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.license.License;
import info.magnolia.license.LicenseManager;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.notification.NotificationTriggerModule;
import com.merkle.oss.magnolia.notification.Notifier;
import com.merkle.oss.magnolia.notification.service.EmailService;

import jakarta.inject.Inject;

public class LicenseExpirationNotifier implements Notifier {
    private final EmailService emailService;
    private final LicenseManager licenseManager;
    private final NotificationTriggerModule notificationTriggerModule;
    private final ServerConfiguration serverConfiguration;

    @Inject
    public LicenseExpirationNotifier(
            final EmailService emailService,
            final LicenseManager licenseManager,
            final NotificationTriggerModule notificationTriggerModule,
            final ServerConfiguration serverConfiguration
    ) {
        this.emailService = emailService;
        this.licenseManager = licenseManager;
        this.notificationTriggerModule = notificationTriggerModule;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public void trigger() {
        final LicenseExpirationNotifierConfig config = notificationTriggerModule.getLicenseConfig();
        if(config.isEnabled()) {
            final License license = licenseManager.getLicense("enterprise");
            Stream
                    .concat(
                            Stream.of(0),
                            config.getReminderInDays().stream()
                    )
                    .forEach(reminder -> trigger(config, license, reminder));
        }
    }

    private void trigger(final LicenseExpirationNotifierConfig config, final License license, final int expirationInDays) {
        if (isExpiringIn(license, expirationInDays)) {
            emailService.send(
                    config.getSubjectTemplate(),
                    config.getBodyTemplate(),
                    notificationTriggerModule.getEmails(),
                    getMailParameters(license, expirationInDays),
                    Collections.emptySet()
            );
        }
    }

    protected Map<String, Object> getMailParameters(final License license, final int expiration) {
        return Map.of(
                "expiration", expiration,
                "license", getLicensePropertiesList(license),
                "instance", serverConfiguration.getDefaultBaseUrl()
        );
    }

    protected String getLicensePropertiesList(final License license){
        return license.getProperties().entrySet().stream()
                .map(entry -> "\n - " + entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(""));
    }

    protected boolean isExpiringIn(final License license, final int expirationInDays) {
        final LocalDate expirationDate = LocalDate.now().plusDays(expirationInDays);
        if (expirationInDays == 0) {
            return license.getValidityEndDate().isBefore(expirationDate);
        }
        return license.getValidityEndDate().equals(expirationDate);
    }
}
