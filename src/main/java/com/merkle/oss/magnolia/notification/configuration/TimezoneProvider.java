package com.merkle.oss.magnolia.notification.configuration;

import java.time.ZoneId;

import jakarta.inject.Provider;

public interface TimezoneProvider extends Provider<ZoneId> {
    class Default implements TimezoneProvider {
        @Override
        public ZoneId get() {
            return ZoneId.systemDefault();
        }
    }
}
