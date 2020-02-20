package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.RequestLog;

public class RequestLogSpecification {

	public static Specification<RequestLog> createTimeBetween(final String startDate, final String endDate) {
        return new Specification<RequestLog>() {
            @Override
            public Predicate toPredicate(Root<RequestLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }


	public static Specification<RequestLog> remoteIpContain(final String remoteIp) {
        return new Specification<RequestLog>() {
            @Override
            public Predicate toPredicate(Root<RequestLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("remoteIp"), "%" + remoteIp + "%");
            }
        };
    }

	public static Specification<RequestLog> requestUrlContain(final String requestUrl) {
        return new Specification<RequestLog>() {
            @Override
            public Predicate toPredicate(Root<RequestLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("requestUrl"), "%" + requestUrl + "%");
            }
        };
    }

	public static Specification<RequestLog> regUserEqual(final Long regUserSeq) {
        return new Specification<RequestLog>() {
            @Override
            public Predicate toPredicate(Root<RequestLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("regUserInfo").get("userSeq"), regUserSeq);
            }
        };
    }

	public static Specification<RequestLog> statusEqual(final String status) {
        return new Specification<RequestLog>() {
            @Override
            public Predicate toPredicate(Root<RequestLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }

}
