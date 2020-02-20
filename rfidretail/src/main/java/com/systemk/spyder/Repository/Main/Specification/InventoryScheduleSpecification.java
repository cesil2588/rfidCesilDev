package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;

public class InventoryScheduleSpecification {
	
	public static Specification<InventoryScheduleHeader> createDateEqual(final String createDate) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<InventoryScheduleHeader> createDateBetween(final String startDate, final String endDate) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<InventoryScheduleHeader> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<InventoryScheduleHeader> companySeqEqual(final Long companySeq) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("companySeq"), companySeq);
            }
        };
    }

    public static Specification<InventoryScheduleHeader> customerCodeEqual(final String customerCode) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("customerCode"), customerCode);
            }
        };
    }
	
	public static Specification<InventoryScheduleHeader> confirmYnEqual(final String confirmYn) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("confirmYn"), confirmYn);
            }
        };
    }
	
	public static Specification<InventoryScheduleHeader> completeYnEqual(final String completeYn) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeYn"), completeYn);
            }
        };
    }
	
	public static Specification<InventoryScheduleHeader> disuseYnEqual(final String disuseYn) {
        return new Specification<InventoryScheduleHeader>() {
            @Override
            public Predicate toPredicate(Root<InventoryScheduleHeader> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("disuseYn"), disuseYn);
            }
        };
    }
}
