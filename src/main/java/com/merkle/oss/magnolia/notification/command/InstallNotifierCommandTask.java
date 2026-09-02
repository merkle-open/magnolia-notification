package com.merkle.oss.magnolia.notification.command;


import com.merkle.oss.magnolia.powernode.NodeOperationFactory;
import com.merkle.oss.magnolia.setup.task.common.AbstractInstallCommandTask;
import com.merkle.oss.magnolia.setup.task.type.InstallAndUpdateTask;

import jakarta.inject.Inject;

public class InstallNotifierCommandTask extends AbstractInstallCommandTask implements InstallAndUpdateTask {
	@Inject
	public InstallNotifierCommandTask(final NodeOperationFactory nodeOperationFactory) {
		super(nodeOperationFactory, NotifierCommand.CATALOG, NotifierCommand.NAME, NotifierCommand.class);
	}
}
