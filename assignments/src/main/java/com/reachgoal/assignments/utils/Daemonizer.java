package com.reachgoal.assignments.utils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Daemonizer {
	private static final Logger logger = LoggerFactory.getLogger(Daemonizer.class);
	protected ExecutorService executor;
	protected List<Callable<Boolean>> tasks;
	protected volatile boolean shutdown = false;

	public void init() {
		for (final Callable<Boolean> t : tasks) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					while (!shutdown) {
						try {
							final Boolean state = t.call();
							if (!state) {
								Thread.sleep(10000);
							}

						} catch (final Exception e) {
							logger.error("Received exception ", e);
						}
					}
				}
			});
		}
	}

	public void stop() {
		executor.shutdown();
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public List<Callable<Boolean>> getTasks() {
		return tasks;
	}

	public void setTasks(List<Callable<Boolean>> tasks) {
		this.tasks = tasks;
	}

}
