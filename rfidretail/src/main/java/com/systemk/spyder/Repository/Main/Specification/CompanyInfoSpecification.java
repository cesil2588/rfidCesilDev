package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.CompanyInfo;

public class CompanyInfoSpecification {

	public static Specification<CompanyInfo> companySeqEqual(final Long companySeq) {
        return new Specification<CompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CompanyInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<CompanyInfo> typeEqual(final String type) {
        return new Specification<CompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CompanyInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("type"), type);
            }
        };
    }
	
	public static Specification<CompanyInfo> telNoContain(final String telNo) {
        return new Specification<CompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CompanyInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("telNo"), "%" + telNo + "%");
            }
        };
    }

}
