package com.merkle.oss.magnolia.notification.command;

import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.jcr.nodebuilder.NodeOperation;
import info.magnolia.jcr.nodebuilder.task.ErrorHandling;
import info.magnolia.module.InstallContext;
import info.magnolia.module.scheduler.JobDefinition;

import java.util.Map;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.powernode.NodeOperationFactory;
import com.merkle.oss.magnolia.setup.task.common.AbstractConfigureSchedulerJobTask;

import jakarta.inject.Inject;

public class NotifierSchedulerSetupTask extends AbstractConfigureSchedulerJobTask {
	private static final String TASK_NAME = "Configure notifier scheduler";
	private static final String TASK_DESCRIPTION = "Configure scheduler job for notifier";
	private static final String CRON_NOTIFY = "0 30 1 ? * * *"; // Daily at 01:30:00am

	private final ServerConfiguration serverConfiguration;

	@Inject
	public NotifierSchedulerSetupTask(
		final NodeOperationFactory nodeOperationFactory,
		final ServerConfiguration serverConfiguration
	) {
		super(nodeOperationFactory, TASK_NAME, TASK_DESCRIPTION, ErrorHandling.logging);
		this.serverConfiguration = serverConfiguration;
	}

	@Override
	protected NodeOperation[] getNodeOperations(final InstallContext ctx) {
		if (serverConfiguration.isAdmin()) {
			return super.getNodeOperations(ctx);
		}
		return new NodeOperation[]{};
	}

	@Override
	protected Stream<JobDefinition> getJobs() {
		final boolean authorInstance = serverConfiguration.isAdmin();
		return Stream
			.of(
				createNotifierJobDefinition()
			)
			.peek(job -> job.setEnabled(authorInstance));
	}

	private JobDefinition createNotifierJobDefinition() {
		final JobDefinition archiveTasksJob = new JobDefinition("triggerNotifierJob", NotifierCommand.CATALOG, NotifierCommand.NAME, CRON_NOTIFY, Map.of());
		archiveTasksJob.setConcurrent(false);
		return archiveTasksJob;
	}
}
