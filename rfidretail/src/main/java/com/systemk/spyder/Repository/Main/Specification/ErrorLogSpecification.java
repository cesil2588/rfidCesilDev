package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ErrorLog;

public class ErrorLogSpecification {
	
	public static Specification<ErrorLog> createTimeBetween(final String startDate, final String endDate) {
        return new Specification<ErrorLog>() {
            @Override
            public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	

	public static Specification<ErrorLog> errorCodeContain(final String errorCode) {
        return new Specification<ErrorLog>() {
            @Override
            public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("errorCode"), "%" + errorCode + "%");
            }
        };
    }
	
	public static Specification<ErrorLog> requestUrlContain(final String requestUrl) {
        return new Specification<ErrorLog>() {
            @Override
            public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("requestUrl"), "%" + requestUrl + "%");
            }
        };
    }
	
	
}
