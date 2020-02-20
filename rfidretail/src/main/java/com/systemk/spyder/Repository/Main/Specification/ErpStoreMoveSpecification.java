package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ErpStoreMove;

public class ErpStoreMoveSpecification {

			
		public static Specification<ErpStoreMove> regDateBetween(final Date startDate, final Date endDate) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.between(root.get("orderRegDate"), startDate, endDate);
	            }
	        };
	    }
		
		public static Specification<ErpStoreMove> completeYn(final String completeYn) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("completeYn"), completeYn);
	            }
	        };
	    }
		
		public static Specification<ErpStoreMove> fromCustomerCode(final String customerCode) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("fromCustomerCode"), customerCode);
	            }
	        };
	    }
		
		public static Specification<ErpStoreMove> fromCornerCode(final String conerCode) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("fromCornerCode"), conerCode);
	            }
	        };
		}
		
		public static Specification<ErpStoreMove> toCustomerCode(final String customerCode) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("toCustomerCode"), customerCode);
	            }
	        };
	    }
		
		public static Specification<ErpStoreMove> toCornerCode(final String conerCode) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("toCornerCode"), conerCode);
	            }
	        };
		}
		
		public static Specification<ErpStoreMove> moveType(final String moveType) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.get("moveType"), moveType);
	            }
	        };
		}
		
		public static Specification<ErpStoreMove> NotMoveType(final String moveType) {
	        return new Specification<ErpStoreMove>() {
	            @Override
	            public Predicate toPredicate(Root<ErpStoreMove> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.notEqual(root.get("moveType"), moveType);
	            }
	        };
		}

}
