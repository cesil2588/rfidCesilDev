package com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(CustomerJobCompletionNotificationListener.class);

	private final JdbcTemplate template;

	@Autowired
	public CustomerJobCompletionNotificationListener(JdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			/*
			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people",
					new RowMapper<Person>() {
						@Override
						public Person mapRow(ResultSet rs, int row) throws SQLException {
							return new Person(rs.getString(1), rs.getString(2));
						}
					});

			for (Person person : results) {
				log.info("Found <" + person + "> in the database.");
			}
			*/
		}
	}
}