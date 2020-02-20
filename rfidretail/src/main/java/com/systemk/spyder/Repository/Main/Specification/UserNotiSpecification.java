package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.UserNoti;

public class UserNotiSpecification {

	public static Specification<UserNoti> userSeqEqual(final Long userSeq) {
        return new Specification<UserNoti>() {
            @Override
            public Predicate toPredicate(Root<UserNoti> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("notiUserInfo").get("userSeq"), userSeq);
            }
        };
    }
	
	public static Specification<UserNoti> checkYnEqual(final String checkYn) {
        return new Specification<UserNoti>() {
            @Override
            public Predicate toPredicate(Root<UserNoti> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("checkYn"), checkYn);
            }
        };
    }
	
	public static Specification<UserNoti> categoryEqual(final String category) {
        return new Specification<UserNoti>() {
            @Override
            public Predicate toPredicate(Root<UserNoti> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("category"), category);
            }
        };
    }

	public static Specification<UserNoti> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<UserNoti>() {
            @Override
            public Predicate toPredicate(Root<UserNoti> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
}
