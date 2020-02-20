package com.systemk.spyder.Config.Filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent>{

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getThreadName().equals("schedulerFactoryBean_QuartzSchedulerThread") ||
			event.getThreadName().contains("QuartzScheduler_")) {
            return FilterReply.DENY;
        } else {
            return FilterReply.ACCEPT;
        }
	}

}
