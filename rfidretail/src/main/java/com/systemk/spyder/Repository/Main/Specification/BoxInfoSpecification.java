package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BoxInfo;

public class BoxInfoSpecification {
	
	public static Specification<BoxInfo> boxWorkGroupSeqEqual(final Long seq) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxWorkGroupSeq"), seq);
            }
        };
    }

	public static Specification<BoxInfo> createDateBetween(final String startDate, final String endDate) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BoxInfo> createDateEqual(final String createDate) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<BoxInfo> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BoxInfo> arrivalDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("arrivalDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BoxInfo> startCompanySeqEqual(final Long companySeq) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<BoxInfo> endCompanySeqEqual(final Long companySeq) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<BoxInfo> typeEqual(final String type) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type"), type);
            }
        };
    }
	
	public static Specification<BoxInfo> statEqual(final String stat) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<BoxInfo> boxNumBetween(final String startBoxNum, final String endBoxNum) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("boxNum"), startBoxNum, endBoxNum);
            }
        };
    }
	
	public static Specification<BoxInfo> boxNumContain(final String boxNum) {
        return new Specification<BoxInfo>() {
            @Override
            public Predicate toPredicate(Root<BoxInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("boxNum"), "%" + boxNum + "%");
            }
        };
    }

}
