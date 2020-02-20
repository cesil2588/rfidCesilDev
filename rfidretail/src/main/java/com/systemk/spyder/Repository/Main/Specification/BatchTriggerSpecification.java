package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BatchTrigger;

public class BatchTriggerSpecification {
	
	public static Specification<BatchTrigger> scheduleDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BatchTrigger>() {
            @Override
            public Predicate toPredicate(Root<BatchTrigger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("scheduleDate"), startDate, endDate);
            }
        };
    }
	
	
	public static Specification<BatchTrigger> userSeqEqual(final Long userSeq) {
        return new Specification<BatchTrigger>() {
            @Override
            public Predicate toPredicate(Root<BatchTrigger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("regUserInfo").get("userSeq"), userSeq);
            }
        };
    }
	

	public static Specification<BatchTrigger> statEqual(final String stat) {
        return new Specification<BatchTrigger>() {
            @Override
            public Predicate toPredicate(Root<BatchTrigger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<BatchTrigger> statusEqual(final String status) {
        return new Specification<BatchTrigger>() {
            @Override
            public Predicate toPredicate(Root<BatchTrigger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }
	
	public static Specification<BatchTrigger> typeEqual(final String type) {
        return new Specification<BatchTrigger>() {
            @Override
            public Predicate toPredicate(Root<BatchTrigger> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type"), type);
            }
        };
    }
}
