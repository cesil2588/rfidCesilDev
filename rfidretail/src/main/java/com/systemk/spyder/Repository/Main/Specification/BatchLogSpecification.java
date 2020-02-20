package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BatchLog;

public class BatchLogSpecification {
	
	public static Specification<BatchLog> createTimeBetween(final Date startTime, final Date endTime) {
        return new Specification<BatchLog>() {
            @Override
            public Predicate toPredicate(Root<BatchLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("startTime"), startTime, endTime);
            }
        };
    }
	

	public static Specification<BatchLog> jobNameContain(final String jobName) {
        return new Specification<BatchLog>() {
            @Override
            public Predicate toPredicate(Root<BatchLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("jobName"), "%" + jobName + "%");
            }
        };
    }
	
	public static Specification<BatchLog> statusContain(final String status) {
        return new Specification<BatchLog>() {
            @Override
            public Predicate toPredicate(Root<BatchLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("status"), "%" + status + "%");
            }
        };
    }
	
	
}
