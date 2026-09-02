package com.merkle.oss.magnolia.notification.configuration;

import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;

import com.google.inject.multibindings.Multibinder;
import com.merkle.oss.magnolia.notification.Notifier;
import com.merkle.oss.magnolia.notification.license.LicenseExpirationNotifier;

public class NotificationGuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
	@Override
	protected void configure() {
		super.configure();
		final Multibinder<Notifier> notifierMultibinder = Multibinder.newSetBinder(binder(), Notifier.class);
		notifierMultibinder.addBinding().to(LicenseExpirationNotifier.class);
	}
}
