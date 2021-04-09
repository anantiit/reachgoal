package com.reachgoal.assignments.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {

	private final ExecutorService executorService;

	public ThreadExecutor(ExecutorService exec) {
		this.executorService = exec;
	}

	public void submitTask(final Runnable command) throws InterruptedException {
		executorService.execute(command);
	}

	public void shutdown() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}
	}

}
