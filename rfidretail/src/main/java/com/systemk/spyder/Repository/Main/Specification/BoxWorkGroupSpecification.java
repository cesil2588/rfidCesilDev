package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BoxWorkGroup;

public class BoxWorkGroupSpecification {

	public static Specification<BoxWorkGroup> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BoxWorkGroup>() {
            @Override
            public Predicate toPredicate(Root<BoxWorkGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BoxWorkGroup> typeEqual(final String type) {
        return new Specification<BoxWorkGroup>() {
            @Override
            public Predicate toPredicate(Root<BoxWorkGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type"), type);
            }
        };
    }
	
	public static Specification<BoxWorkGroup> startCompanySeqEqual(final Long companySeq) {
        return new Specification<BoxWorkGroup>() {
            @Override
            public Predicate toPredicate(Root<BoxWorkGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<BoxWorkGroup> endCompanySeqEqual(final Long companySeq) {
        return new Specification<BoxWorkGroup>() {
            @Override
            public Predicate toPredicate(Root<BoxWorkGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<BoxWorkGroup> statEqual(final String stat) {
        return new Specification<BoxWorkGroup>() {
            @Override
            public Predicate toPredicate(Root<BoxWorkGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
}
