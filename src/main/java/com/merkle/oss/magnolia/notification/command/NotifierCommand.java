package com.merkle.oss.magnolia.notification.command;

import info.magnolia.commands.MgnlCommand;
import info.magnolia.context.Context;

import java.lang.invoke.MethodHandles;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.notification.Notifier;

import jakarta.inject.Inject;

public class NotifierCommand extends MgnlCommand {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Set<Notifier> notifiers;

    public static final String NAME = "triggerNotifications";
    public static final String CATALOG = "default";

    @Inject
    public NotifierCommand(final Set<Notifier> notifiers) {
        this.notifiers = notifiers;
    }

    @Override
    public boolean execute(final Context context) {
        LOG.debug("triggering notifications");
        for(Notifier notifier: notifiers) {
            try {
                notifier.trigger();
            } catch (Exception e) {
                LOG.error("Failed to trigger {}!", notifier.getClass(), e);
            }
        }
        LOG.debug("triggered notifications");
        return true;
    }
}
