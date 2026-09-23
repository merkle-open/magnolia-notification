package com.merkle.oss.magnolia.notification.license;

import java.util.Set;

public class LicenseExpirationNotifierConfig {
    private boolean enabled;
    private boolean additionallySendToLicenseOwner;
    private Set<Integer> reminderInDays;
    private String subjectTemplate;
    private String bodyTemplate;

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdditionallySendToLicenseOwner() {
        return additionallySendToLicenseOwner;
    }
    public void setAdditionallySendToLicenseOwner(final boolean additionallySendToLicenseOwner) {
        this.additionallySendToLicenseOwner = additionallySendToLicenseOwner;
    }

    public Set<Integer> getReminderInDays() {
        return reminderInDays;
    }
    public void setReminderInDays(final Set<Integer> reminderInDays) {
        this.reminderInDays = reminderInDays;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }
    public void setSubjectTemplate(final String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }
    public void setBodyTemplate(final String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }
}
