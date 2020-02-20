package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.systemk.spyder.Entity.Main.MailLog;


public class MailLogSpecification {
	
	public static Specification<MailLog> typeEqual(final String type) {
        return new Specification<MailLog>() {
            @Override
            public Predicate toPredicate(Root<MailLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	return cb.equal(root.get("type"), type);
            }
        };
    }
	
	public static Specification<MailLog> statEqual(final String stat) {
        return new Specification<MailLog>() {
            @Override
            public Predicate toPredicate(Root<MailLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<MailLog> sendDateBetween(final Date startDate, final Date endDate) {
        return new Specification<MailLog>() {
            @Override
            public Predicate toPredicate(Root<MailLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("sendDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<MailLog> mailFromContain(final String mailForm) {
        return new Specification<MailLog>() {
            @Override
            public Predicate toPredicate(Root<MailLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("mailFrom"), "%" + mailForm + "%");
            }
        };
    }
	
	public static Specification<MailLog> mailSendContain(final String mailSend) {
        return new Specification<MailLog>() {
            @Override
            public Predicate toPredicate(Root<MailLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("mailSend"), "%" + mailSend + "%");
            }
        };
    }

}
