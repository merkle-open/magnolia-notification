package com.merkle.oss.magnolia.notification;

import info.magnolia.module.ModuleLifecycle;

import java.util.Set;

import com.merkle.oss.magnolia.notification.license.LicenseExpirationNotifierConfig;

import jakarta.annotation.Nullable;

public class NotificationTriggerModule implements ModuleLifecycle {
    @Nullable
	private String sender;
    private Set<String> emails;
    private LicenseExpirationNotifierConfig licenseConfig;

	@Nullable
	public String getSender() {
		return sender;
	}
	public void setSender(@Nullable final String sender) {
		this.sender = sender;
	}

	public Set<String> getEmails() {
		return emails;
	}
	public void setEmails(final Set<String> emails) {
        this.emails = emails;
    }

	public LicenseExpirationNotifierConfig getLicenseConfig() {
		return licenseConfig;
	}
	public void setLicenseConfig(final LicenseExpirationNotifierConfig licenseConfig) {
		this.licenseConfig = licenseConfig;
	}
}
