package com.myapp.task.config.scheduler.task;

import com.myapp.task.config.scheduler.DynamicAbstractScheduler;
import com.myapp.task.manage.board.attach.AttachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class AttachFileDelete extends DynamicAbstractScheduler {
	private static final Logger logger;

	static {
		logger = LoggerFactory.getLogger(AttachFileDelete.class);
	}

	@Autowired
	private AttachService attachService;

	@Override
	public void runner() {
		try {
			if(this.attachService.deleteAttachFiles(null)) logger.info("TRASH ATTACH FILE DELETE BATCH SUCCESS");
			else logger.info("TRASH ATTACH FILE DELETE BATCH FAIL");
		} catch (Exception e) {
			logger.error("TRASH ATTACH FILE DELETE BATCH ERROR : ", e);
		}
	}

	@Override
	public Trigger getTrigger() {
		return new CronTrigger("0 */1 * * * *");
	}

	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		super.restartScheduler();
	}
}
