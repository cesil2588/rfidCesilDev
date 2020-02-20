package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;
public class ErpStoreReturnInfoSpecification {
		
	public static Specification<ErpStoreReturnInfo> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<ErpStoreReturnInfo>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreReturnInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("erpRegDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<ErpStoreReturnInfo> returnType(final String returnType) {
        return new Specification<ErpStoreReturnInfo>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreReturnInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("returnType"), returnType);
            }
        };
    }
	
	public static Specification<ErpStoreReturnInfo> fromCustomerCode(final String customerCode) {
        return new Specification<ErpStoreReturnInfo>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreReturnInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("fromCustomerCode"), customerCode);
            }
        };
    }
	
	public static Specification<ErpStoreReturnInfo> fromCornerCode(final String conerCode) {
        return new Specification<ErpStoreReturnInfo>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreReturnInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("fromCornerCode"), conerCode);
            }
        };
    }
	
}

