package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.AppInfo;

public class AppInfoSpecification {

	public static Specification<AppInfo> typeEqual(final String type) {
        return new Specification<AppInfo>() {
            @Override
            public Predicate toPredicate(Root<AppInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type"), type);
            }
        };
    }
	
	public static Specification<AppInfo> representYnEqual(final String representYnEqual) {
        return new Specification<AppInfo>() {
            @Override
            public Predicate toPredicate(Root<AppInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("representYn"), representYnEqual);
            }
        };
    }
	
	public static Specification<AppInfo> useYnEqual(final String representYnEqual) {
        return new Specification<AppInfo>() {
            @Override
            public Predicate toPredicate(Root<AppInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("useYn"), representYnEqual);
            }
        };
    }
	
	public static Specification<AppInfo> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<AppInfo>() {
            @Override
            public Predicate toPredicate(Root<AppInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
}
