package com.myapp.task.config.scheduler;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public abstract class DynamicAbstractScheduler {
	private ThreadPoolTaskScheduler scheduler;

	public void stopScheduler() {
		if(this.scheduler == null) return;
		this.scheduler.shutdown();
	}

	public void startScheduler() {
		this.scheduler = new ThreadPoolTaskScheduler();
		this.scheduler.initialize();
		this.scheduler.schedule(getRunnable(), getTrigger());
	}

	public void restartScheduler() {
		this.stopScheduler();
		this.startScheduler();
	}

	private Runnable getRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				runner();
			}
		};
	}

	public abstract void runner();

	public abstract Trigger getTrigger();
}