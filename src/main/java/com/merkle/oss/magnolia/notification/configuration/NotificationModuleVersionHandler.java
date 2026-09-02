package com.merkle.oss.magnolia.notification.configuration;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;
import info.magnolia.module.model.Version;

import java.util.List;

import com.merkle.oss.magnolia.notification.command.InstallNotifierCommandTask;
import com.merkle.oss.magnolia.notification.command.NotifierSchedulerSetupTask;

import jakarta.inject.Inject;

public class NotificationModuleVersionHandler extends DefaultModuleVersionHandler {
	private final List<Task> tasks;

	@Inject
	public NotificationModuleVersionHandler(
			final InstallNotifierCommandTask installNotifierCommandTask,
			final NotifierSchedulerSetupTask notifierSchedulerSetupTask
	) {
		this.tasks = List.of(installNotifierCommandTask, notifierSchedulerSetupTask);
	}

	@Override
	protected final List<Task> getExtraInstallTasks(final InstallContext installContext) { // when module node does not exist
		return tasks;
	}

	@Override
	protected final List<Task> getDefaultUpdateTasks(final Version forVersion) { //on every module update
		return tasks;
	}
}
