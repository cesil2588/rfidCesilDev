package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.StoreMoveLog;

public class StoreMoveLogSpecification {
	
	public static Specification<StoreMoveLog> createDateEqual(final String createDate) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<StoreMoveLog> createDateBetween(final String startDate, final String endDate) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<StoreMoveLog> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog > root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<StoreMoveLog> startCompanySeqEqual(final Long companySeq) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxInfo").get("startCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<StoreMoveLog> endCompanySeqEqual(final Long companySeq) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxInfo").get("endCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<StoreMoveLog> startConfirmYnEqual(final String confirmYn) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startConfirmYn"), confirmYn);
            }
        };
    }
	
	public static Specification<StoreMoveLog> startWorkYnEqual(final String startWorkYn) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startWorkYn"), startWorkYn);
            }
        };
    }
	
	public static Specification<StoreMoveLog> endConfirmYnEqual(final String confirmYn) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endConfirmYn"), confirmYn);
            }
        };
    }
	
	public static Specification<StoreMoveLog> endWorkYnEqual(final String endWorkYn) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endWorkYn"), endWorkYn);
            }
        };
    }
	
	public static Specification<StoreMoveLog> disuseYnEqual(final String disuseYn) {
        return new Specification<StoreMoveLog>() {
            @Override
            public Predicate toPredicate(Root<StoreMoveLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("disuseYn"), disuseYn);
            }
        };
    }
}
