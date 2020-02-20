package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.UserInfo;

public class UserInfoSpecification {

	public static Specification<UserInfo> companySeqEqual(final Long companySeq) {
        return new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<UserInfo> roleEqual(final String role) {
        return new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("roleInfo").get("role"), role);
            }
        };
    }
	
	public static Specification<UserInfo> checkYnEqual(final String checkYn) {
        return new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("checkYn"), checkYn);
            }
        };
    }
	
	public static Specification<UserInfo> userIdContain(final String userId) {
        return new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("userId"), "%" + userId + "%");
            }
        };
    }

}
